package com.example.scrap_chef.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    //    private String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private String SECRET_KEY = "591DF9176D24FABE8E9CB1B37334EA";
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 86400000L; // 1일 (24시간)
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 2592000000L; // 30일 (30일)

    // Access Token 생성
    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Access Token과 Refresh Token을 둘 다 검증하는 메서드
    public boolean validateToken(String token, Authentication authentication) {
        String username = getUsernameFromToken(token);
        return (username.equals(authentication.getName()) && !isTokenExpired(token));
    }

    // isTokenExpired, getExpirationDateFromToken만 private으로 정의한 이유
    // 두 메서드는 토큰 유효성 검증 로직 내부에서만 사용되기 때문
    // 캡슐화를 구현

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    // 토큰 만료 시간 추출
    private Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }
}
