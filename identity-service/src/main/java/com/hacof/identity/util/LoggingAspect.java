package com.hacof.identity.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ActivityLogRequest;
import com.hacof.identity.service.ActivityLogService;

@Aspect
@Component
public class LoggingAspect {
    private final ActivityLogService activityLogService;
    private final HttpServletRequest request;

    public LoggingAspect(ActivityLogService activityLogService, HttpServletRequest request) {
        this.activityLogService = activityLogService;
        this.request = request;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @AfterReturning("controllerMethods()")
    public void logAfterRequest(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String action = detectAction(joinPoint);
        String target = className + "." + methodName;

        Long userId = getUserIdFromSecurityContext();

        ActivityLogRequest logRequest = new ActivityLogRequest();
        logRequest.setAction(action);
        logRequest.setTarget(target);
        logRequest.setIpAddress(request.getRemoteAddr());
        logRequest.setDeviceDetails(request.getHeader("User-Agent"));
        logRequest.setStatus(Status.SUCCESS);
        logRequest.setChangedFields(getChangedFields(joinPoint));

        activityLogService.logActivity(userId, logRequest);
    }

    private String detectAction(JoinPoint joinPoint) {
        if (joinPoint.getSignature().getDeclaringType().isAnnotationPresent(PostMapping.class)) return "CREATE";
        if (joinPoint.getSignature().getDeclaringType().isAnnotationPresent(PutMapping.class)
                || joinPoint.getSignature().getDeclaringType().isAnnotationPresent(PatchMapping.class)) return "UPDATE";
        if (joinPoint.getSignature().getDeclaringType().isAnnotationPresent(DeleteMapping.class)) return "DELETE";
        return "READ";
    }

    private Map<String, Object> getChangedFields(JoinPoint joinPoint) {
        Map<String, Object> changes = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            changes.put("parameters", Arrays.toString(args));
        }
        return changes;
    }

    private Long getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getTokenAttributes();
            return ((Number) claims.get("user_id")).longValue();
        }
        return 1L;
    }
}
