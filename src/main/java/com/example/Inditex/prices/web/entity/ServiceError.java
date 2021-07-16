package com.example.Inditex.prices.web.entity;



public enum ServiceError {
    UNKNOWN_ERROR(500, "Unknown error");


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
