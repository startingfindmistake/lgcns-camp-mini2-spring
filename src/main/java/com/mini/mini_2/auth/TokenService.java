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

    @Value("${jwt.access-ttl-ms}")
    private long ACCESS_TOKEN_VALIDITY_MS;
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

        redisTemplate.opsForValue().set(accessKey(token), userId, ACCESS_TOKEN_VALIDITY_MS, TimeUnit.MILLISECONDS);
        return token;
    }

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

        redisTemplate.opsForValue().set(refreshKey(token), userId, REFRESH_TOKEN_VALIDITY_MS, TimeUnit.MILLISECONDS);
        return token;
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
                    
            Object userId = redisTemplate.opsForValue().get(accessKey(token));
            return userId != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) { return validateAccessToken(token); }

    public void invalidateToken(String token) { invalidateAccessToken(token); }

    public void invalidateAccessToken(String token) {
        redisTemplate.delete(accessKey(token));
    }

    public void invalidateRefreshToken(String token) {
        redisTemplate.delete(refreshKey(token));
    }

    public TokenPair refreshWithRotation(String refreshToken) {
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

        Object val = redisTemplate.opsForValue().get(refreshKey(refreshToken));
        if (val == null) return null;

        invalidateRefreshToken(refreshToken);
        String newRefresh = generateRefreshToken(userId);
        String newAccess = generateAccessToken(userId);

        return new TokenPair(newAccess, newRefresh);
    }

    public record TokenPair(String accessToken, String refreshToken) {}

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
