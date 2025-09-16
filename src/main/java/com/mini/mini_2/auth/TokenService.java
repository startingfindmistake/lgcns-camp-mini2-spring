package com.mini.mini_2.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 토큰 유효기간 (예: 1시간)
    private final long TOKEN_VALIDITY = 60 * 60 * 1000L;

    public TokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성 및 Redis 저장
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY);

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        // Redis에 저장 (key: token, value: userId, TTL: 토큰 만료와 동일)
        redisTemplate.opsForValue().set(token, userId, TOKEN_VALIDITY, TimeUnit.MILLISECONDS);
        return token;
    }

    // JWT 및 Redis 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            // Redis에 토큰이 존재하는지 확인
            Object userId = redisTemplate.opsForValue().get(token);
            return userId != null;
        } catch (Exception e) {
            return false;
        }
    }

    // 로그아웃 등 토큰 무효화
    public void invalidateToken(String token) {
        redisTemplate.delete(token);
    }

    // (선택) 토큰에서 userId 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
