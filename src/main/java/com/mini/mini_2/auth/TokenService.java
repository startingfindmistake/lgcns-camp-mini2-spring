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

    // 액세스 토큰 유효기간 (기본: 1시간) - ms
    @Value("${jwt.access-ttl-ms}")
    private long ACCESS_TOKEN_VALIDITY_MS;
    // 리프레시 토큰 유효기간 (기본: 14일) - ms
    @Value("${jwt.refresh-ttl-ms}")
    private long REFRESH_TOKEN_VALIDITY_MS;

    public TokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private String accessKey(String token) { return "access:" + token; }
    private String refreshKey(String token) { return "refresh:" + token; }

    // 액세스 토큰 생성 및 Redis 저장
    public String generateAccessToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS);

        String token = Jwts.builder()
                .setSubject(userId)
                .claim("type", "access")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        // Redis에 저장 (key: access:<token>, value: userId)
        redisTemplate.opsForValue().set(accessKey(token), userId, ACCESS_TOKEN_VALIDITY_MS, TimeUnit.MILLISECONDS);
        return token;
    }

    // 리프레시 토큰 생성 및 Redis 저장
    public String generateRefreshToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS);

        String token = Jwts.builder()
                .setSubject(userId)
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        // Redis에 저장 (key: refresh:<token>, value: userId)
        redisTemplate.opsForValue().set(refreshKey(token), userId, REFRESH_TOKEN_VALIDITY_MS, TimeUnit.MILLISECONDS);
        return token;
    }

    // 액세스 토큰 검증
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            // Redis에 토큰이 존재하는지 확인
            Object userId = redisTemplate.opsForValue().get(accessKey(token));
            return userId != null;
        } catch (Exception e) {
            return false;
        }
    }

    // 기존 이름 유지(호환), 내부적으로 access 검증 호출
    public boolean validateToken(String token) { return validateAccessToken(token); }

    // 로그아웃 등 토큰 무효화
    public void invalidateToken(String token) { invalidateAccessToken(token); }

    public void invalidateAccessToken(String token) {
        redisTemplate.delete(accessKey(token));
    }

    public void invalidateRefreshToken(String token) {
        redisTemplate.delete(refreshKey(token));
    }

    // 리프레시 토큰으로 액세스 토큰 재발급 (회전)
    public TokenPair refreshWithRotation(String refreshToken) {
        // 1) 서명/만료 검증
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        String userId = claims.getSubject();

        // 2) Redis에 리프레시 존재 여부 확인
        Object val = redisTemplate.opsForValue().get(refreshKey(refreshToken));
        if (val == null) return null;

        // 3) 회전: 기존 리프레시 제거 후 새 리프레시 생성
        invalidateRefreshToken(refreshToken);
        String newRefresh = generateRefreshToken(userId);
        String newAccess = generateAccessToken(userId);

        return new TokenPair(newAccess, newRefresh);
    }

    public record TokenPair(String accessToken, String refreshToken) {}

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
