package com.priya.otc.orchestrator.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Simple JWT validation filter.
 * Runs once per request before controller logic.
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${security.jwt.secret}")
    private String jwtSecret; // injected from application-dev.yaml

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. If there is a Bearer token, try to validate it
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7); // remove "Bearer "

            try {
                // 3. Build parser with our secret key
                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token);

                // 4. Extract claims (payload)
                Claims claims = jwsClaims.getBody();
                String username = claims.getSubject(); // "sub" claim

                // 5. Here we could read roles from claims, but for POC we just assign ROLE_USER
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                // 6. Build Authentication object and set it into SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                        null, authorities);

                // Simple log to see who is authenticated
                System.out.println("[JWT] Authenticated user: " + username);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                // If token is invalid/expired/tampered:
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return; // STOP filter chain here, do not call controller
            }
        } else {
            // No Authorization header or not Bearer -> we will let SecurityConfig decide
        }

        // 7. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
