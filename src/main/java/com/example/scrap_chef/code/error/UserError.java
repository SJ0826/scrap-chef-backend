package com.example.scrap_chef.code.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserError implements ErrorCode {
    NOT_FOUND_USER(3000, "유저를 찾을 수 없습니다."),
    DUPLICATED_LOGIN_ID(3002, "이미 등록된 로그인 아이디입니다."),
    INVALID_CREDENTIALS(3003, "아이디 또는 비밀번호가 잘못되었습니다."),
    NOT_ENROLL_USER(3004, "가입되지 않은 유저입니다."),
    ALREADY_SIGN_UP(3005, "이미 가입된 유저입니다.");


    private final int code;
    private final String message;
}
