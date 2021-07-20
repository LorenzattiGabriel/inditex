package com.example.Inditex.prices.exceptions.rule;

public class BusinessRulesError {

    public enum BusinessError {

        GROUP_NOT_FOUND(" the brand does not exist in database"),
        PRICE_NOT_FOUND(" the price does not exist in database"),
        CURRENCY_NOT_FOUND (" currency not found"),

        PRODUCT_NOT_FOUND(" the product does not exist in database");

        private final String errorDescription;

        BusinessError(String errorDescription) {
            this.errorDescription = errorDescription;
        }

        public String getErrorDescription() {
            return errorDescription;
        }
    }
}
