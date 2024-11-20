package com.example.scrap_chef.service;

import com.example.scrap_chef.code.error.AuthError;
import com.example.scrap_chef.data.auth.CheckDuplicateUserOutDto;
import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.domain.user.User;
import com.example.scrap_chef.exception.BadRequestException;
import com.example.scrap_chef.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // repository
    private final UserRepository userRepository;

    /**
     * 사용자 회원가입
     */
    public void signupUser(SignupInDto signupInDto) {
        log.info(signupInDto.getLoginId());
        // 아이디 중복을 체크합니다.
        if (userRepository.existsByLoginId(signupInDto.getLoginId())) {
            throw new BadRequestException(AuthError.ALREADY_ENROLL_ID);
        }

        // User 객체를 생성해 데이터베이스에 저장합니다.
        User user = new User();
        user.setLoginId(signupInDto.getLoginId());
        user.setPassword(signupInDto.getPassword());

        // 데이터베이스에 저장
        userRepository.save(user);
    }

    /**
     * 유저 계정의 로그안 아이디 중복을 체크합니다.
     */
    public String isUserLoginIdDuplicate(String loginId) {
        boolean isDuplicate = userRepository.existsByLoginId(loginId);
        System.out.println(loginId);
        return isDuplicate ? "이미 사용중인 아이디입니다" : "사용 가능합니다";
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
