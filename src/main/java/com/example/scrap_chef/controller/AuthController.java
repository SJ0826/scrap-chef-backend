package com.example.scrap_chef.controller;

import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.domain.user.LoginRequestDto;
import com.example.scrap_chef.domain.user.TokenResponseDto;
import com.example.scrap_chef.dto.ApiResponse;
import com.example.scrap_chef.service.AuthService;
import com.example.scrap_chef.service.UserService;
import com.example.scrap_chef.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static com.example.scrap_chef.util.ResponseUtil.success;

@Tag(name = "인증")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;
    private AuthService authService;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody @Valid SignupInDto request) {
        userService.signupUser(request.getUsername(), request.getPassword());
        return success();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<TokenResponseDto>> signIn(@RequestBody LoginRequestDto request) {
        try {
            TokenResponseDto tokens = authService.signIn(request);
            ApiResponse<TokenResponseDto> response = new ApiResponse<TokenResponseDto>("성공", tokens);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            TokenResponseDto tokens = new TokenResponseDto();
            ApiResponse<TokenResponseDto> response = new ApiResponse<>("실패", tokens);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
