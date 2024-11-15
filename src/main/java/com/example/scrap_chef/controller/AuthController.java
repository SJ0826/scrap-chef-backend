package com.example.scrap_chef.controller;

import com.example.scrap_chef.domain.user.LoginRequestDto;
import com.example.scrap_chef.domain.user.TokenResponseDto;
import com.example.scrap_chef.domain.user.UserRegistrationRequestDto;
import com.example.scrap_chef.dto.ApiResponse;
import com.example.scrap_chef.service.AuthService;
import com.example.scrap_chef.service.UserService;
import com.example.scrap_chef.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRegistrationRequestDto request) {
        try {
            System.out.println("check");
            userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
            ApiResponse<String> response = new ApiResponse<>("회원가입이 완료되었습니다.", null);
            System.out.println(response);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("회원가입 중 오류가 발생했습니다.", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
