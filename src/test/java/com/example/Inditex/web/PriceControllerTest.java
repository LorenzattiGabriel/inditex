package com.example.Inditex.web;

import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.exceptions.TechnicalException;
import com.example.Inditex.prices.exceptions.rule.BusinessRulesError;
import com.example.Inditex.prices.services.PriceService;
import com.example.Inditex.prices.web.PriceController;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.ResponseError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;


import static com.example.Inditex.prices.web.entity.ServiceError.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class PriceControllerTest {

    public static final String EUR = "EUR";
    public static final String ENTER_A_EXISTENT_PRODUCT_ID = "Enter a existent product id";
    public static final String ASK_YOUR_ADMINISTRATOR = "Ask your administrator";
    public static final String ENTER_A_CORRECT_PARAMETERS_OF_SEARCH = "Enter a correct parameters of search";

    @Mock
    private PriceService priceService;

    private PriceController priceController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        priceController = new PriceController(priceService);
    }

    @Test
    public void TestReturnPriceSaved() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;
        PriceResponse priceResponse = new PriceResponse(1L, 1L, startDate, endDate, 1, finalPrice);

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(startDate.toInstant(ZoneOffset.UTC)));


        //when
        when(priceService.savePrice(any())).thenReturn(priceResponse);

        ResponseEntity<?> price = priceController.newPrice(priceIncomingDto);

        //then
        assertSame(price.getStatusCode(), HttpStatus.OK);
        assertNotNull(price.getBody());
    }

    @Test
    public void TestReturnHTTPNotFound() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;
        PriceResponse priceResponse = new PriceResponse(1L, 1L, startDate, endDate, 1, finalPrice);

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(startDate.toInstant(ZoneOffset.UTC)));


        //when
        doThrow(new BusinessException(BusinessRulesError.BusinessError.PRODUCT_NOT_FOUND)).when(priceService).savePrice(any());

        ResponseEntity<?> price = priceController.newPrice(priceIncomingDto);
        ResponseError responseError = (ResponseError) price.getBody();

        //then
        assertNotNull(responseError);
        assertSame(ENTER_A_EXISTENT_PRODUCT_ID, responseError.getAction());
        assertSame(BUSINESS_PRODUCT_NOT_FOUND.getErrorCode(), responseError.getErrorCode());
        assertSame(BUSINESS_PRODUCT_NOT_FOUND.getErrorDescription(), responseError.getErrorMessage());
    }

    @Test
    public void TestReturnHTTPTechnicalError() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(startDate.toInstant(ZoneOffset.UTC)));


        //when
        doThrow(new TechnicalException("test", new Exception())).when(priceService).savePrice(any());

        ResponseEntity<?> price = priceController.newPrice(priceIncomingDto);
        ResponseError responseError = (ResponseError) price.getBody();

        //then
        assertNotNull(responseError);
        assertSame(ASK_YOUR_ADMINISTRATOR, responseError.getAction());
        assertSame(TECHNICAL_ERROR.getErrorCode(), responseError.getErrorCode());
        assertSame(TECHNICAL_ERROR.getErrorDescription(), responseError.getErrorMessage());
    }

    @Test
    public void TestReturnHTTPUnknownError() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;

        PriceIncomingDto priceIncomingDto = new PriceIncomingDto();
        priceIncomingDto.setBrand(1);
        priceIncomingDto.setProduct(1);
        priceIncomingDto.setStartDate(Date.from(startDate.toInstant(ZoneOffset.UTC)));


        //when
        doThrow(new NullPointerException()).when(priceService).savePrice(any());

        ResponseEntity<?> price = priceController.newPrice(priceIncomingDto);
        ResponseError responseError = (ResponseError) price.getBody();

        //then
        assertNotNull(responseError);
        assertSame(ASK_YOUR_ADMINISTRATOR, responseError.getAction());
        assertSame(UNKNOWN_ERROR.getErrorCode(), responseError.getErrorCode());
        assertSame(UNKNOWN_ERROR.getErrorDescription(), responseError.getErrorMessage());
    }

    @Test
    public void TestReturnACorrectPriceFounded() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;
        PriceResponse priceResponse = new PriceResponse(1L, 1L, startDate, endDate, 1, finalPrice);

        //when
        when(priceService.getPrice(any(), any(), any())).thenReturn(priceResponse);

        ResponseEntity<?> price = priceController.getPrices(1, 1, Date.from(startDate.toInstant(ZoneOffset.UTC)));
        PriceResponse body = (PriceResponse) price.getBody();

        //then
        assertSame(price.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
    }

    @Test
    public void TestReturnANotFoundWhenPriceIsNotFounded() {
        //Given
        LocalDateTime startDate = LocalDateTime.of(2020, Calendar.JULY, 14, 15, 50);
        LocalDateTime endDate = LocalDateTime.of(2020, Calendar.JULY, 15, 17, 50);

        float finalPrice = (float) 18.50;
        PriceResponse priceResponse = new PriceResponse(1L, 1L, startDate, endDate, 1, finalPrice);

        //when
        doThrow(new BusinessException(BusinessRulesError.BusinessError.PRICE_NOT_FOUND)).when(priceService).getPrice(any(), any(), any());
        ;

        ResponseEntity<?> price = priceController.getPrices(1, 1, Date.from(startDate.toInstant(ZoneOffset.UTC)));
        ResponseError responseError = (ResponseError) price.getBody();
        //then
        assertSame(price.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(responseError);
        assertSame(ENTER_A_CORRECT_PARAMETERS_OF_SEARCH, responseError.getAction());
        assertSame(BUSINESS_PRICE_NOT_FOUND.getErrorCode(), responseError.getErrorCode());
        assertSame(BUSINESS_PRICE_NOT_FOUND.getErrorDescription(), responseError.getErrorMessage());
    }

}