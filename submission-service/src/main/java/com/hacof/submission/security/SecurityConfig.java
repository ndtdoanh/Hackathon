package com.hacof.submission.security;

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
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/evaluationcriteria/**")
                        .permitAll()
                        .requestMatchers("/api/v1/evaluationscores/**")
                        .permitAll()
                        .requestMatchers("/api/v1/mentorbookings/**")
                        .permitAll()
                        .requestMatchers("/api/v1/submissionevaluations/**")
                        .permitAll()
                        .requestMatchers("/api/v1/submissions/**")
                        .permitAll()
                        .requestMatchers("/api/v1/submissionfiles/upload")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
