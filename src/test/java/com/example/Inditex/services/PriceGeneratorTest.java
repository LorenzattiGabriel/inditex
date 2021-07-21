package com.example.Inditex.services;

import com.example.Inditex.config.PriceConfig;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.model.Brand;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.model.Product;
import com.example.Inditex.prices.repository.BrandRepository;
import com.example.Inditex.prices.repository.ProductRepository;
import com.example.Inditex.prices.generator.PriceGenerator;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PriceGeneratorTest {

    public static final String EUR = "EUR";

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceConfig priceConfig;

    PriceGenerator priceGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        priceGenerator = new PriceGenerator(priceConfig, brandRepository, productRepository);
    }

    @Test
    public void TestGetCorrectPriceDWhenABrandAndProductISFounded() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(1L, "shirt");

        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        priceIncomingDto.setStartDate(Date.from(startDate.toInstant(ZoneOffset.UTC)));


        float finalPrice = (float) 22.5;

        Prices expectedPrice = new Prices(1L, brand, startDate, endDate, 0, product, 1, finalPrice, EUR);

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.of(brand));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(priceConfig.getCurr()).thenReturn(EUR);
        when(priceConfig.getTaxes()).thenReturn((float) 0.21);


        Prices price = priceGenerator.getPriceForCurrentBrand(priceIncomingDto);

        //then
        assertEquals(expectedPrice.getBrand().getId(), price.getBrand().getId());
        assertEquals(expectedPrice.getPriceList(), price.getPriceList());
        assertEquals(expectedPrice.getStartDate(), price.getStartDate());
        assertEquals(expectedPrice.getProduct().getId(), price.getProduct().getId());
    }

    @Test(expected = BusinessException.class)
    public void TestThrowExceptionWhenBrandIsNotFounded() {
        //Given
        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.empty());

        priceGenerator.getPriceForCurrentBrand(priceIncomingDto);
    }

    @Test(expected = BusinessException.class)
    public void TestThrowExceptionWhenProductIsNotFounded() {
        //Given
        Brand brand = new Brand(1L, "zara");

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.of(brand));
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        priceGenerator.getPriceForCurrentBrand(priceIncomingDto);
    }

    @Test(expected = TechnicalException.class)
    public void TestThrowExceptionWhenDateISInvalid() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(1L, "shirt");

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.of(brand));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        priceGenerator.getPriceForCurrentBrand(priceIncomingDto);
    }

    @Test(expected = BusinessException.class)
    public void TestThrowExceptionWhenTaxesConfigIsInvalid() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(1L, "shirt");

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.of(brand));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(priceConfig.getTaxes()).thenReturn(null);

        priceGenerator.getPriceForCurrentBrand(priceIncomingDto);
    }

    @Test(expected = BusinessException.class)
    public void TestThrowExceptionWhenCurrencyConfigIsInvalid() {
        //Given
        Brand brand = new Brand(1L, "zara");
        Product product = new Product(1L, "shirt");

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(Instant.now()));

        //when
        when(brandRepository.findById(any())).thenReturn(Optional.of(brand));
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(priceConfig.getTaxes()).thenReturn((float) 0.21);
        when(priceConfig.getCurr()).thenReturn(null);

        priceGenerator.getPriceForCurrentBrand(priceIncomingDto);
    }


}