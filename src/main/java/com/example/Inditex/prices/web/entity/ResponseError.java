package com.example.Inditex.prices.web.entity;

public class ResponseError {

    private final Integer errorCode;
    private final String errorMessage;
    private final String action;

    public ResponseError(Integer errorCode, String errorMessage, String action) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.action = action;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getAction() {
        return action;
    }
}
