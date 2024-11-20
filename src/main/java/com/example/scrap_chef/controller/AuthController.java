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


//    @PostMapping("/sign-in")
//    public ResponseEntity<ApiResponse<TokenResponseDto>> signIn(@RequestBody LoginRequestDto request) {
//        try {
//            TokenResponseDto tokens = authService.signIn(request);
//            ApiResponse<TokenResponseDto> response = new ApiResponse<TokenResponseDto>("성공", tokens);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (Exception e) {
//            TokenResponseDto tokens = new TokenResponseDto();
//            ApiResponse<TokenResponseDto> response = new ApiResponse<>("실패", tokens);
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//    }

}
