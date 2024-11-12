package com.example.scrap_chef.controller;

import com.example.scrap_chef.domain.user.LoginRequestDto;
import com.example.scrap_chef.domain.user.UserRegistrationRequestDto;
import com.example.scrap_chef.dto.ApiResponse;
import com.example.scrap_chef.service.UserService;
import com.example.scrap_chef.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    @PostMapping("/register")
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

}
