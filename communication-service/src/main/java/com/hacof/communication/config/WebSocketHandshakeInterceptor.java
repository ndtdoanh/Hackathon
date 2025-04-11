package com.hacof.communication.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Value("${jwt.signerKey}")
    String signerKey;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.debug("Performing handshake for WebSocket connection");

        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");

            if (token != null && !token.isEmpty()) {
                try {
                    SignedJWT signedJWT = SignedJWT.parse(token);
                    MACVerifier verifier = new MACVerifier(signerKey.getBytes());

                    if (signedJWT.verify(verifier)) {
                        String username = signedJWT.getJWTClaimsSet().getSubject();
                        Object rolesObj = signedJWT.getJWTClaimsSet().getClaim("role");

                        if (username != null) {
                            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            if (rolesObj instanceof List<?> roles) {
                                authorities = roles.stream()
                                        .map(Object::toString)
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
                            }

                            UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(username, null, authorities);

                            attributes.put("SPRING_SECURITY_CONTEXT", auth);
                            return true;
                        }
                    }
                } catch (ParseException | JOSEException e) {
                    log.error("Error parsing JWT token during WebSocket handshake", e);
                }
            } else {
                log.warn("No token found in WebSocket connection request");
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}