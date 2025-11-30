package com.priya.otc.orchestrator.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TokenGenerator {

    public static void main(String[] args) {
        String secret = "my-super-secret-key-123456789012345"; // same as in application-dev.yaml

        long now = System.currentTimeMillis();
        long expiryMillis = now + (1000 * 60 * 60); // 1 hour

        String token = Jwts.builder()
                .setSubject("otc-api-client") // "sub"
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiryMillis))
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();

        System.out.println("JWT Token:");
        System.out.println(token);
    }
}
