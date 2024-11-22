package com.example.scrap_chef.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;


@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSignInKey() { // JWT 서명을 위해 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 엑세스 토큰을 생성합니다.
     */
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .subject(username) // payload
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * 리프레시 토큰을 생성합니다.
     */
    public String generateRefreshToken() {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * JWT에서 클레임을 추출합니다.
     */
    public Claims extractAllClaims(String token) {
        return (Jwts.parser()
                .verifyWith(getSignInKey())
                .build())
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * JWT 토큰을 검증합니다.
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
