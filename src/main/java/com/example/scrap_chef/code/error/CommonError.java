package com.example.scrap_chef.code.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements ErrorCode {
    UNAUTHORIZED(9, "Unauthorized");

    private final int code;
    private final String message;

}
