package com.hacof.communication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Tắt CSRF Protection
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/v1/tasks/**")
                                .permitAll() // Mở toàn bộ quyền truy cập Task API
                                .anyRequest()
                                .permitAll() // Cho phép tất cả request khác
                        )
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không sử dụng session
                        );

        return http.build();
    }
}
