package com.example.Inditex.services;

import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.model.Brand;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.model.Product;
import com.example.Inditex.prices.repository.PriceRepository;
import com.example.Inditex.prices.services.PriceGenerator;
import com.example.Inditex.prices.services.PriceService;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.web.entity.PriceResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


import static org.mockito.Mockito.*;


public class PriceServiceTest {

    public static final String EUR = "EUR";

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceGenerator priceGenerator;

    private PriceService priceService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceService(priceRepository, priceGenerator);
    }

    @Test
    public void TestGetCorrectlyPriceDtoWhenAPriceIsSaved() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(1L, "shirt");

        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        float finalPrice = (float) 22.5;

        Prices prices = new Prices(1L, brand, startDate, endDate, 1, product, 1, finalPrice, EUR);

        //when
        when(priceGenerator.getPriceForCurrentBrand(priceIncomingDto)).thenReturn(prices);
        when(priceRepository.save(any())).thenReturn(prices);

        PriceResponse priceResponse = priceService.savePrice(priceIncomingDto);

        //then
        assertEquals(prices.getBrand().getId(), priceResponse.getBrandId());
        assertEquals(prices.getEndDate(), priceResponse.getEndDate());
        assertEquals(prices.getPriceList(), priceResponse.getPriceList());
        assertEquals(prices.getStartDate(), priceResponse.getStartDate());
        assertEquals(prices.getProduct().getId(), priceResponse.getProductId());
        assertEquals(BigDecimal.valueOf(prices.getFinalPrice()), BigDecimal.valueOf(priceResponse.getPrice()));
    }

    @Test(expected = TechnicalException.class)
    public void TestThrowExceptionWhenTryToSaveAPrice() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(3455L, "shirt");

        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        float finalPrice = (float) 22.5;

        Prices prices = new Prices(1L, brand, startDate, endDate, 1, product, 1, finalPrice, EUR);

        //when
        when(priceGenerator.getPriceForCurrentBrand(priceIncomingDto)).thenThrow(new TechnicalException("time out", new Exception()));
        priceService.savePrice(priceIncomingDto);
    }

    @Test
    public void TestGetCorrectlyPriceDtoWhenAPriceIsFounded() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(3455L, "shirt");
        Product otherProduct = new Product(34566L, "jean");

        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);
        float finalPrice = (float) 22.5;

        Prices pricesWithLessPriority = new Prices(1L, brand, startDate, endDate, 1, product, 0, finalPrice, EUR);
        Prices pricesWithMorePriority = new Prices(1L, brand, startDate, endDate, 1, otherProduct, 1, finalPrice, EUR);

        LinkedList<Prices> pricesList = new LinkedList<>();
        pricesList.add(pricesWithLessPriority);
        pricesList.add(pricesWithMorePriority);

        //when
        when(priceGenerator.convertToDateTime(any(Date.class))).thenReturn(startDate);
        when(priceRepository.findPrices(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(pricesList);


        PriceResponse priceResponse = priceService.getPrice(brand.getId().intValue(),
                product.getId().intValue(), new Date());

        //then
        assertEquals(pricesWithMorePriority.getBrand().getId(), priceResponse.getBrandId());
        assertEquals(pricesWithMorePriority.getEndDate(), priceResponse.getEndDate());
        assertEquals(pricesWithMorePriority.getPriceList(), priceResponse.getPriceList());
        assertEquals(pricesWithMorePriority.getStartDate(), priceResponse.getStartDate());
        assertEquals(pricesWithMorePriority.getProduct().getId(), priceResponse.getProductId());
        assertEquals(BigDecimal.valueOf(pricesWithMorePriority.getFinalPrice()), BigDecimal.valueOf(priceResponse.getPrice()));
    }

    @Test(expected = BusinessException.class)
    public void TestThrowExceptionWhenAPriceIsNotFounded() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(3455L, "shirt");
        Product otherProduct = new Product(34566L, "jean");

        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        //when
        when(priceGenerator.convertToDateTime(any(Date.class))).thenReturn(startDate);
        when(priceRepository.findPrices(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Collections.emptyList());

        priceService.getPrice(brand.getId().intValue(), product.getId().intValue(), new Date());
    }
}