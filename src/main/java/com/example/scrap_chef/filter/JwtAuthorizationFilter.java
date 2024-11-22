package com.example.scrap_chef.filter;

import com.example.scrap_chef.config.ApiConstants;
import com.example.scrap_chef.config.CustomUserDetails;
import com.example.scrap_chef.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher(); // 파일 경로나 URL 패턴 매칭

    private final JwtTokenUtil jwtTokenUtil;
    private final PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 스웨거 관련 경로는 JWT 필터를 통과하지 않게 설정
        String requestURI = request.getRequestURI();
        if (isWhitelistedPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우, 예외 처리
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        try {
            String token = authorizationHeader.substring(7);

            if (!jwtTokenUtil.isTokenValid(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            UserDetails userDetails = getUserDetailsFromToken(token);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    null
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isWhitelistedPath(String requestURI) {
        for (String path : ApiConstants.WHITELISTED_PATHS) {
            if (pathMatcher.match(path, requestURI)) {
                return true;
            }
        }
        return false;
    }

    private UserDetails getUserDetailsFromToken(String token) {
        String subject = jwtTokenUtil.extractSubject(token);
        return CustomUserDetails.builder()
                .loginId(subject)
                .build();
    }
}
