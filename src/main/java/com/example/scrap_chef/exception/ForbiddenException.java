package com.example.scrap_chef.exception;

import com.example.scrap_chef.code.error.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final int code;
    private final String message;

    public ForbiddenException(ErrorCode error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
