package com.hacof.identity.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ActivityLogRequest;
import com.hacof.identity.dto.request.AuthenticationRequest;
import com.hacof.identity.entity.User;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.ActivityLogService;

@Aspect
@Component
public class LoggingAspect {
    private final ActivityLogService activityLogService;
    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoggingAspect(
            ActivityLogService activityLogService, HttpServletRequest request, UserRepository userRepository) {
        this.activityLogService = activityLogService;
        this.request = request;
        this.userRepository = userRepository;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @AfterReturning(value = "controllerMethods()", returning = "response")
    public void logAfterRequest(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String action = detectAction(joinPoint);
        String target = className + "." + methodName;

        Long userId = getUserIdFromSecurityContext();
        Map<String, Object> changedFields = getChangedFields(joinPoint, response, action);

        ActivityLogRequest logRequest = new ActivityLogRequest();
        logRequest.setAction(action);
        logRequest.setTarget(target);
        logRequest.setIpAddress(request.getRemoteAddr());
        logRequest.setDeviceDetails(request.getHeader("User-Agent"));
        logRequest.setStatus(Status.SUCCESS);
        logRequest.setChangedFields(changedFields);

        activityLogService.logActivity(userId, logRequest);
    }

    private String detectAction(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(PostMapping.class)) return "CREATE";
        if (method.isAnnotationPresent(PutMapping.class) || method.isAnnotationPresent(PatchMapping.class))
            return "UPDATE";
        if (method.isAnnotationPresent(DeleteMapping.class)) return "DELETE";
        return "READ";
    }

    private Map<String, Object> getChangedFields(JoinPoint joinPoint, Object response, String action) {
        Map<String, Object> changes = new HashMap<>();
        Object[] args = joinPoint.getArgs();

        if ("authenticate".equalsIgnoreCase(joinPoint.getSignature().getName()) && args.length > 0) {
            if (args[0] instanceof AuthenticationRequest authRequest) {
                changes.put("username", authRequest.getUsername());
                changes.put("password", hashPassword(authRequest.getPassword()));
            }
            return changes;
        }

        if ("READ".equals(action)) {
            changes.put("parameters", args);
        } else if ("CREATE".equals(action) || "UPDATE".equals(action)) {
            changes.put("parameters", args);
            changes.put("after", extractResponseData(response));
        } else if ("DELETE".equals(action)) {
            if (args.length > 0) {
                changes.put("before", getExistingData(args));
            }
        }

        return changes;
    }

    private Object extractResponseData(Object response) {
        if (response instanceof ResponseEntity<?> entity) {
            return entity.getBody();
        }
        return response;
    }

    private Object getExistingData(Object[] args) {
        if (args.length == 0) return null;

        Object entity = args[0];
        if (entity instanceof Long id) {
            Optional<User> user = userRepository.findById(id);
            return user.map(User::toString).orElse("Not Found");
        }
        return entity;
    }

    private Long getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getTokenAttributes();
            return ((Number) claims.get("user_id")).longValue();
        }
        return 1L;
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
