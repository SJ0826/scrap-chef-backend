package com.example.scrap_chef.service;

import com.example.scrap_chef.code.error.CommonError;
import com.example.scrap_chef.code.error.UserError;
import com.example.scrap_chef.data.auth.ReissueTokenOutDto;
import com.example.scrap_chef.data.auth.SignInInDto;
import com.example.scrap_chef.data.auth.SignInOutDto;
import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.entity.User;
import com.example.scrap_chef.entity.UserRefreshToken;
import com.example.scrap_chef.exception.BadRequestException;
import com.example.scrap_chef.exception.UnauthorizedException;
import com.example.scrap_chef.repository.UserRefreshTokenRepository;
import com.example.scrap_chef.repository.UserRepository;
import com.example.scrap_chef.util.EncryptUtil;
import com.example.scrap_chef.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // repository
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    // service & util
    private final JwtTokenUtil jwtTokenUtil;

    /*
     * 유저 회원가입
     */
    public void signupUser(SignupInDto signupInDto) {


        // 로그인 아이디가 이미 설정된 경우, 예외처리를 합니다.
        if (userRepository.existsByLoginId(signupInDto.getLoginId())) {
            throw new BadRequestException(UserError.ALREADY_SIGN_UP);
        }

        try {
            User newUser = new User();
            newUser.signUp(signupInDto);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(UserError.DUPLICATED_LOGIN_ID);
        }
    }

    /*
     * 유저 로그인
     */
    public SignInOutDto signInUser(SignInInDto signInInDto) {

        User user = userRepository
                .findByLoginId(signInInDto.getLoginId())
                .orElseThrow(() -> new BadRequestException(UserError.INVALID_CREDENTIALS));

        // 아이디, 비밀번호 설정 확인
        if (user.getLoginId().isEmpty()) {
            throw new BadRequestException(UserError.NOT_ENROLL_USER);
        }

        // 비밀번호가 일치하지 않는 경우 예외처리
        if (!user.getPassword().equals(EncryptUtil.encryptSha512(signInInDto.getPassword()))) {
            throw new BadRequestException(UserError.INVALID_CREDENTIALS);
        }

        String accessToken = jwtTokenUtil.generateAccessToken(user.getLoginId());
        String refreshToken = jwtTokenUtil.generateRefreshToken();

        LocalDateTime expiredAt = jwtTokenUtil.extractClaim(refreshToken, Claims::getExpiration)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        userRefreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(existingToken -> existingToken.updateRefreshToken(refreshToken, expiredAt),
                        () -> userRefreshTokenRepository.save(UserRefreshToken.create(user, refreshToken, expiredAt)));

        return SignInOutDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    /*
     * 유저 계정의 로그인 아이디 중복을 체크합니다.
     */
    public String isUserLoginIdDuplicate(String loginId) {
        boolean isDuplicate = userRepository.existsByLoginId(loginId);
        return isDuplicate ? "이미 사용중인 아이디입니다" : "사용 가능합니다";
    }


    /**
     * 토큰을 재발급 합니다.
     */
    @Transactional
    public ReissueTokenOutDto reissueToken(String token) {
        // 토큰 유효성 체크

        if (!jwtTokenUtil.isTokenValid(token)) {
            throw new UnauthorizedException(CommonError.UNAUTHORIZED);
        }

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new UnauthorizedException(CommonError.UNAUTHORIZED));

        // 토큰이 일치하는지 확인
        if (!userRefreshToken.getRefreshToken().equals(token)) {
            throw new UnauthorizedException(CommonError.UNAUTHORIZED);
        }

        // 토큰이 만료되었는지 확인
        if (userRefreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException(CommonError.UNAUTHORIZED);
        }

        String accessToken = jwtTokenUtil.generateAccessToken(jwtTokenUtil.extractAllClaims(token).getSubject());
        String refreshToken = jwtTokenUtil.generateRefreshToken();

        Date expiredDate = jwtTokenUtil.extractClaim(token, Claims::getExpiration);
        LocalDateTime expiredAt = LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault());

        userRefreshToken.updateRefreshToken(refreshToken, expiredAt);


        return ReissueTokenOutDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
