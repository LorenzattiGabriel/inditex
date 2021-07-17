package com.example.Inditex.prices.exceptions;


import com.example.Inditex.prices.exceptions.rule.BusinessRulesError.BusinessError;

public class BusinessException extends RuntimeException {

    private final BusinessError businessError;

    public BusinessException(BusinessError businessError) {
        this.businessError = businessError;
    }

    public BusinessError getBusinessError() {
        return businessError;
    }

    @Override
    public String getMessage() {
        return businessError.getErrorDescription();
    }

}
