package com.example.scrap_chef.service;

import com.example.scrap_chef.domain.user.LoginRequestDto;
import com.example.scrap_chef.domain.user.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public TokenResponseDto signIn(LoginRequestDto loginRequestDto) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        System.out.println(authentication);
        // JWT 토큰 생성
//        String accessToken = jwtTokenUtil.generateAccessToken(authentication);
//        String refreshToken = jwtTokenUtil.generateRefreshToken(authentication);

        TokenResponseDto tokenResponse = new TokenResponseDto();
        tokenResponse.setAccessToken("");
        tokenResponse.setRefreshToken("");
        return tokenResponse;
    }
}
