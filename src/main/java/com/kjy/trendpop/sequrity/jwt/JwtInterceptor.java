package com.kjy.trendpop.sequrity.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer로 시작하지 않는 경우
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  // 요청 차단
        }

        // JWT 토큰 추출
        String token = authorizationHeader.substring(7);

        try {
            // JWT에서 사용자명 추출
            String userId = jwtUtil.extractUserId(token);

            // 사용자명이 없거나 토큰이 만료된 경우
            if (userId == null || jwtUtil.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;  // 요청 차단
            }
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 401 응답 처리
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
            return false;
        } catch (Exception e) {
            // 다른 JWT 파싱 오류 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            return false;
        }
        // 요청이 성공적으로 통과하면 true 반환
        return true;
    }
}
