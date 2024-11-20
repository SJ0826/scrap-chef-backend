package com.example.scrap_chef.service;

import com.example.scrap_chef.code.error.AuthError;
import com.example.scrap_chef.code.error.UserError;
import com.example.scrap_chef.data.auth.SignInInDto;
import com.example.scrap_chef.data.auth.SignInOutDto;
import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.domain.user.User;
import com.example.scrap_chef.exception.BadRequestException;
import com.example.scrap_chef.repository.UserRepository;
import com.example.scrap_chef.util.ApiResponse;
import com.example.scrap_chef.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // repository
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    /*
     * 유저 회원가입
     */
    public void signupUser(SignupInDto signupInDto) {
        // 아이디 중복을 체크합니다.
        if (userRepository.existsByLoginId(signupInDto.getLoginId())) {
            throw new BadRequestException(AuthError.ALREADY_ENROLL_ID);
        }

        // User 객체를 생성해 데이터베이스에 저장합니다.
        User user = new User();
        user.setLoginId(signupInDto.getLoginId());
        user.setPassword(signupInDto.getPassword());

        userRepository.save(user);
    }

    /*
     * 유저 로그인
     */
    public SignInOutDto signInUser(SignInInDto signInInDto) {
        User user = userRepository
                .findByLoginId(signInInDto.getLoginId())
                .orElseThrow(() -> new BadRequestException(UserError.INVALID_CREDENTIALS));

        return SignInOutDto
                .builder()
                .accessToken(jwtTokenUtil.generateAccessToken(user.getLoginId()))
                .refreshToken(jwtTokenUtil.generateRefreshToken(user.getLoginId()))
                .build();
    }


    /*
     * 유저 계정의 로그인 아이디 중복을 체크합니다.
     */
    public String isUserLoginIdDuplicate(String loginId) {
        boolean isDuplicate = userRepository.existsByLoginId(loginId);
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
