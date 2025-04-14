// package com.hacof.communication.config;
//
// import com.hacof.communication.repository.UserRepository;
// import com.hacof.communication.util.AuditContext;
// import com.hacof.communication.util.SecurityUtil;
// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.stomp.StompCommand;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.messaging.support.MessageHeaderAccessor;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
//
// @Component
// @RequiredArgsConstructor
// @Slf4j
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class WebSocketChannelInterceptor implements ChannelInterceptor {
//
//    UserRepository userRepository;
//    SecurityUtil securityUtil;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//        if (accessor != null) {
//            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                log.debug("WebSocket CONNECT: Setting user in AuditContext");
//                setAuditUserFromHeader(accessor);
//            } else if (StompCommand.SEND.equals(accessor.getCommand())) {
//                log.debug("WebSocket SEND: Setting user in AuditContext");
//                setAuditUserFromHeader(accessor);
//            } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                log.debug("WebSocket SUBSCRIBE: Setting user in AuditContext");
//                setAuditUserFromHeader(accessor);
//            }
//        }
//
//        return message;
//    }
//
//    @Override
//    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
//        AuditContext.clear();
//    }
//
//    private void setAuditUserFromHeader(StompHeaderAccessor accessor) {
//        try {
//            Object authentication = accessor.getUser();
//            if (authentication != null) {
//                SecurityContextHolder.getContext().setAuthentication((UsernamePasswordAuthenticationToken)
// authentication);
//                securityUtil.setAuditUser();
//            } else {
//                log.warn("No authentication found in WebSocket headers");
//            }
//        } catch (Exception e) {
//            log.error("Error setting user in AuditContext during WebSocket communication", e);
//        }
//    }
// }
