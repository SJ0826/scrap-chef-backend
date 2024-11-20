package com.example.scrap_chef.code.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthError implements ErrorCode {
    ALREADY_ENROLL_ID(10, "이미 사용중인 아이디입니다.");

    private final int code;
    private final String message;
}
