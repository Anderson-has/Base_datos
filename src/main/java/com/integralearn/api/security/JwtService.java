package com.integralearn.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-minutes:60}")
    private long expirationMinutes;

    private SecretKey key() {
        // Para HS256 se recomienda >= 32 bytes
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String username, Set<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60);
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("roles", roles)
                .signWith(key())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token, String expectedUsername) {
        try {
            var claims = Jwts.parser().verifyWith(key()).build()
                    .parseSignedClaims(token).getPayload();
            return expectedUsername.equals(claims.getSubject()) && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
