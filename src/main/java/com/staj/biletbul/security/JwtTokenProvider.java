package com.staj.biletbul.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSecretKey())
                .compact();

        return token;
    }

    public String extractUsernameFromToken(String token) {
        String username = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return username;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired or invalid JWT token", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT token", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Security exception", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Illegal JWT token", e);
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
