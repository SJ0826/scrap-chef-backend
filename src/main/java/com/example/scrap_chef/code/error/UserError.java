package com.example.scrap_chef.code.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserError implements ErrorCode {
    INVALID_CREDENTIALS(3003, "아이디 또는 비밀번호가 잘못되었습니다.");

    private final int code;
    private final String message;
}
