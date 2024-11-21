package com.example.scrap_chef.exception;

import com.example.scrap_chef.code.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final int code;
    private final String message;

    public UnauthorizedException(ErrorCode error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public UnauthorizedException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
