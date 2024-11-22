package com.example.scrap_chef.code.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonError implements ErrorCode {
    UNAUTHORIZED(9, "Unauthorized"),
    ENCRYPT_FAIL(10, "암호화에 실패했습니다");


    private final int code;
    private final String message;

}
