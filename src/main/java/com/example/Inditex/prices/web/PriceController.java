package com.example.Inditex.prices.web;


import com.example.Inditex.prices.exceptions.rule.BusinessRulesError.*;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.model.Prices;
import com.example.Inditex.prices.services.PriceService;
import com.example.Inditex.prices.web.entity.ResponseError;
import com.example.Inditex.prices.web.entity.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collections;
import java.util.List;

import static com.example.Inditex.prices.web.entity.ServiceError.*;

@RestController
@RequestMapping(value = "/inditex/prices/")
public class PriceController {

    private PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newPrice(@Valid @RequestBody PriceIncomingDto priceIncomingDto) {
        ResponseEntity<?> responseEntity;
        try {
            final PriceResponse priceResponse = priceService.savePrice(priceIncomingDto);
            responseEntity = new ResponseEntity<>(priceResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            responseEntity = createResponse400(e.getBusinessError());
        } catch (Exception e) {
            responseEntity = createResponse500(UNKNOWN_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public List<Prices> getPrices() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Prices> getPrice(@PathVariable("id") Long id) {
        return null;
    }


    private ResponseEntity<ResponseError> createResponse400(BusinessError businessError) {
        ServiceError serviceError;
        HttpStatus restStatus;
        String userAction;

        switch (businessError) {
            case GROUP_NOT_FOUND:
                serviceError = BUSINESS_BRAND_NOT_FOUND;
                restStatus = HttpStatus.BAD_REQUEST;
                userAction = "Enter a existent brand id";
                break;
            case PRODUCT_NOT_FOUND:
                serviceError = BUSINESS_PRODUCT_NOT_FOUND;
                restStatus = HttpStatus.BAD_REQUEST;
                userAction = "Enter a existent product id";
                break;
            case CURRENCY_NOT_FOUND:
                serviceError = BUSINESS_CURRENCY_NOT_FOUND;
                restStatus = HttpStatus.BAD_REQUEST;
                userAction = "Enter a existent product id";
                break;
            default:
                throw new RuntimeException("business rules error");
        }

        ResponseError responseError = new ResponseError(
                serviceError.getErrorCode(), serviceError.getErrorDescription(), userAction);

        return new ResponseEntity<>(responseError, restStatus);
    }

    private ResponseEntity<ResponseError> createResponse500(ServiceError serviceError) {

        ResponseError dthProfileResponseError = new ResponseError(
                serviceError.getErrorCode(), serviceError.getErrorDescription(), "Ask your administrator");

        return new ResponseEntity<>(dthProfileResponseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

