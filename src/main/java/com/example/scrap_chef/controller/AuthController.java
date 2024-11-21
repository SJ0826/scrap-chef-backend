package com.example.scrap_chef.controller;

import com.example.scrap_chef.data.auth.*;
import com.example.scrap_chef.util.ApiResponse;
import com.example.scrap_chef.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.scrap_chef.util.ResponseUtil.success;

@Slf4j
@Tag(name = "인증")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    // Service & Util
    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody @Valid SignupInDto signupInDto) {
        authService.signupUser(signupInDto);
        return success();
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInOutDto>> signInUser(@Valid @RequestBody SignInInDto signInInDto) {
        return success(authService.signInUser(signInInDto));
    }

    @Operation(summary = "아이디 중복 체크")
    @PostMapping("/duplicate-check")
    public ResponseEntity<ApiResponse<Object>> checkUserDuplicate(@RequestBody CheckDuplicateUserInDto checkDuplicateUserInDto) {
        return success(authService.isUserLoginIdDuplicate(checkDuplicateUserInDto.getLoginId()), null);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<ApiResponse<ReissueTokenOutDto>> reissueTokens(
            @RequestHeader("Authorization") String authorization) {
        return success(authService.reissueToken(authorization.substring(7)));
    }
}
