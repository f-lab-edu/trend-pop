package com.kjy.trendpop.sequrity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    // 토큰 생성
    public String generateToken(String id) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id);
    }

    // Key 객체 생성 (SECRET_KEY를 바이트 배열로 변환한 후 KeySpec으로 만듬)
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);  // Base64 디코딩
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());  // byte array -> Key
    }

    // 토큰 생성 로직
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)  // 페이로드 설정 (사용자 정보 등)
                .setSubject(subject)  // 주체 설정 (사용자 ID 등)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))  // 토큰 만료 시간 1분 (테스트 위해 1분으로 설정함)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // 서명 생성 (SecretKeySpec 사용)
                .compact();  // JWT 토큰을 문자열로 변환
    }

    // 토큰에서 사용자 이름 추출
    public String extractUserId(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 처리
            throw e;
        }
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // 토큰 유효성 확인
    public boolean validateToken(String token, String id) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(id) && !isTokenExpired(token));
    }

    // 토큰에서 페이로드 클레임 추출
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())  // Key 객체 사용
                    .build()  // 빌더 완료
                    .parseClaimsJws(token)  // JWT 토큰 파싱
                    .getBody();  // 페이로드에서 클레임 추출
        } catch (ExpiredJwtException e) {
            throw e;  // 예외를 상위 호출로 전달
        }
    }
}
