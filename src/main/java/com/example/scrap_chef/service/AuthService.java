package com.example.scrap_chef.service;

import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.domain.user.LoginRequestDto;
import com.example.scrap_chef.domain.user.TokenResponseDto;
import com.example.scrap_chef.domain.user.User;
import com.example.scrap_chef.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // Service & Util
    private final PasswordEncoder passwordEncoder;

    // repository
    private final UserRepository userRepository;


    public void signupUser(SignupInDto signupInDto) {

        // 비밀번호를 암호화합니다.
        String encryptedPassword = passwordEncoder.encode(signupInDto.getPassword());

        // User 객체를 생성해 데이터베이스에 저장합니다.
        User user = new User();
        user.setUsername(signupInDto.getUsername());
        user.setPassword(encryptedPassword);

        // 데이터베이스에 저장
        userRepository.save(user);
    }

//    public TokenResponseDto signIn(LoginRequestDto loginRequestDto) {
//        // 사용자 인증
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
//        );
//
//        System.out.println(authentication);
//        // JWT 토큰 생성
////        String accessToken = jwtTokenUtil.generateAccessToken(authentication);
////        String refreshToken = jwtTokenUtil.generateRefreshToken(authentication);
//
//        TokenResponseDto tokenResponse = new TokenResponseDto();
//        tokenResponse.setAccessToken("");
//        tokenResponse.setRefreshToken("");
//        return tokenResponse;
//    }
}
