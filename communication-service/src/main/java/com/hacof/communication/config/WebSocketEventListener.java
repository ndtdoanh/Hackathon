package com.hacof.communication.config;

import com.hacof.communication.util.AuditContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.debug("WebSocket connected");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.debug("WebSocket disconnected");
        AuditContext.clear();
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.debug("WebSocket subscribe to: {}", headerAccessor.getDestination());
    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.debug("WebSocket unsubscribe from: {}", headerAccessor.getDestination());
    }
}