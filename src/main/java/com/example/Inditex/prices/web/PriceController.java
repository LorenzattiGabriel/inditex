package com.example.Inditex.prices.web;

import com.example.Inditex.prices.exceptions.rule.BusinessRulesError.*;
import com.example.Inditex.prices.exceptions.*;
import com.example.Inditex.prices.web.entity.PriceResponse;
import com.example.Inditex.prices.web.entity.PriceIncomingDto;
import com.example.Inditex.prices.services.PriceService;
import com.example.Inditex.prices.web.entity.ResponseError;
import com.example.Inditex.prices.web.entity.ServiceError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.Date;

import static com.example.Inditex.prices.web.entity.ServiceError.*;

@Api(value = "Price Management System", description = "Several operations in a list of prices")
@RestController
@RequestMapping(value = "/inditex/prices/")
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @ApiOperation(value = "Save a new price", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved price"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Business Exception"),
            @ApiResponse(code = 500, message = "Technical Exception"),
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newPrice(@Valid @RequestBody PriceIncomingDto priceIncomingDto) {
        ResponseEntity<?> responseEntity;
        try {
            final PriceResponse priceResponse = priceService.savePrice(priceIncomingDto);
            responseEntity = new ResponseEntity<>(priceResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            responseEntity = createResponse400(e.getBusinessError());
        } catch (TechnicalException e) {
            responseEntity = createResponse500(TECHNICAL_ERROR);

        } catch (Exception e) {
            responseEntity = createResponse500(UNKNOWN_ERROR);
        }
        return responseEntity;
    }

    @ApiOperation(value = "get price filtered by parameters", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get filtered price"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Business Exception"),
            @ApiResponse(code = 500, message = "Technical Exception"),
    })
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> getPrices(@RequestParam @NotNull @Positive Integer brandId,
                                       @RequestParam @NotNull @Positive Integer productId,
                                       @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                               Date startDate) {
        ResponseEntity<?> responseEntity;
        try {
            final PriceResponse priceResponse = priceService.getPrice(brandId, productId, startDate);
            responseEntity = new ResponseEntity<>(priceResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            responseEntity = createResponse400(e.getBusinessError());
        } catch (TechnicalException e) {
            responseEntity = createResponse500(TECHNICAL_ERROR);
        } catch (Exception e) {
            responseEntity = createResponse500(UNKNOWN_ERROR);
        }
        return responseEntity;
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
            case PRICE_NOT_FOUND:
                serviceError = BUSINESS_PRICE_NOT_FOUND;
                restStatus = HttpStatus.BAD_REQUEST;
                userAction = "Enter a correct parameters of search";
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

