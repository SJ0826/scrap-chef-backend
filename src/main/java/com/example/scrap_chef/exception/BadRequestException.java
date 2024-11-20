package com.example.scrap_chef.exception;

import com.example.scrap_chef.code.error.ErrorCode;

public class BadRequestException extends RuntimeException {
    private final int code;
    private final String message;

    public BadRequestException(ErrorCode error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
