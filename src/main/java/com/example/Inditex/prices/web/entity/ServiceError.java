package com.example.Inditex.prices.web.entity;


public enum ServiceError {

    BUSINESS_BRAND_NOT_FOUND(404, "Brand  not found"),
    BUSINESS_PRICE_NOT_FOUND(404, "Brand  not found"),
    BUSINESS_PRODUCT_NOT_FOUND(404, "Product  not found"),
    BUSINESS_CURRENCY_NOT_FOUND(404, "Currency  not found"),
    TECHNICAL_ERROR(500, "Technical Error"),
    UNKNOWN_ERROR(500, "Unknown Error");

    private final Integer errorCode;
    private final String errorDescription;

    ServiceError(Integer errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
