package com.densoft.portfolio.exceptions;

public class ApIException extends RuntimeException {
    private String message;


    public ApIException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
