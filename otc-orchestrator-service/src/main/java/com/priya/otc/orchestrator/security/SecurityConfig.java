package com.priya.otc.orchestrator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 1. For APIs, we usually disable CSRF (we use tokens instead, not cookies)
                .csrf(csrf -> csrf.disable())

                // 2. Define URL access rules
                .authorizeHttpRequests(auth -> auth
                        // health/info or swagger could be public if you wish
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        // All OTC APIs must be authenticated
                        .requestMatchers("/api/otc/**").authenticated()
                        // everything else allowed (for now)
                        .anyRequest().permitAll())

                // 3. Insert our JWT filter before the default auth filter
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                // 4. We don't use form login / basic login here, but keep basics enabled
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
