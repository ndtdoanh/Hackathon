package com.hacof.hackathon.configs;

// @Configuration
// EnableWebSecurity
// @EnableMethodSecurity
// public class SecurityConfig {
//
//    private static final String[] PUBLIC_ENDPOINTS = {
//        "/api/v1/auth/token",
//        "/api/v1/auth/introspect",
//        "/api/v1/auth/refresh",
//        "/api/v1/auth/logout",
//        "/api/v1/auth/outbound/authentication",
//        "/v3/api-docs/**",
//        "/swagger-ui/**",
//        "/swagger-ui/index.html",
//        "/api/v1/*"
//    };
//
//    private final CustomJwtDecoder customJwtDecoder;
//
//    public SecurityConfig(CustomJwtDecoder customJwtDecoder) {
//        this.customJwtDecoder = customJwtDecoder;
//    }
//
//    //    @Bean
//    //    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    //        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_ENDPOINTS)
//    //                .permitAll()
//    //                .anyRequest()
//    //                .authenticated());
//    //
//    //        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
//    //
//    // jwtConfigurer.decoder(customJwtDecoder).jwtAuthenticationConverter(jwtAuthenticationConverter())));
//    //
//    //        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//    //
//    //        return httpSecurity.build();
//    //    }
//    //
//    //    @Bean
//    //    JwtAuthenticationConverter jwtAuthenticationConverter() {
//    //        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//    //        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
//    //        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");
//    //
//    //        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//    //        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//    //
//    //        return jwtAuthenticationConverter;
//    //    }
// }
