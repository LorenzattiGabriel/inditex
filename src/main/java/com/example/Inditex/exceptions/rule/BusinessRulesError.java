package com.example.Inditex.exceptions.rule;

public class BusinessRulesError {

    public enum BusinessError {

        ID_FORMAT("");

        private final String errorDescription;

        BusinessError(String errorDescription) {
            this.errorDescription = errorDescription;
        }

        public String getErrorDescription() {
            return errorDescription;
        }
    }
}
