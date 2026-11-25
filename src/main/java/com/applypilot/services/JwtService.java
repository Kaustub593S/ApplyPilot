package com.applypilot.services;

import com.applypilot.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expiryMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-expiry-minutes}") long expiryMinutes) {

        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expiryMinutes = expiryMinutes;
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getId().toString())      // who the token is for
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiryMinutes, ChronoUnit.MINUTES)))
                .signWith(key)                          // signature made with the secret
                .compact();
    }

    public long getExpirySeconds()
    {
        return expiryMinutes * 60;
    }

    public UUID extractUserId(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(key)          // checks the signature using your secret
                .build()
                .parseSignedClaims(token) // also checks expiry; throws if invalid or expired
                .getPayload();
        return UUID.fromString(claims.getSubject());
    }

    public String extractRole(String token)
    {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload()
                .get("role", String.class);
    }
}