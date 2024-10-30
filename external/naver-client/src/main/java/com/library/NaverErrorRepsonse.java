package com.library;

public class NaverErrorRepsonse {
    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private String errorMessage;
    private String errorCode;

    public NaverErrorRepsonse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
