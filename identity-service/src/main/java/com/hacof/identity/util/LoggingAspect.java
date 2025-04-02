package com.hacof.identity.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ActivityLogRequest;
import com.hacof.identity.entity.User;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.ActivityLogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

// @Aspect
// @Component
// public class LoggingAspect {
//    private final ActivityLogService activityLogService;
//    private final HttpServletRequest request;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public LoggingAspect(
//            ActivityLogService activityLogService, HttpServletRequest request, UserRepository userRepository) {
//        this.activityLogService = activityLogService;
//        this.request = request;
//        this.userRepository = userRepository;
//    }
//
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void controllerMethods() {}
//
//    @AfterReturning(value = "controllerMethods()", returning = "response")
//    public void logAfterRequest(JoinPoint joinPoint, Object response) {
//        String methodName = joinPoint.getSignature().getName();
//        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String action = detectAction(joinPoint);
//        String target = className + "." + methodName;
//
//        Long userId = getUserIdFromSecurityContext();
//        Map<String, Object> changedFields = getChangedFields(joinPoint, response, action);
//
//        ActivityLogRequest logRequest = new ActivityLogRequest();
//        logRequest.setAction(action);
//        logRequest.setTarget(target);
//        logRequest.setIpAddress(request.getRemoteAddr());
//        logRequest.setDeviceDetails(request.getHeader("User-Agent"));
//        logRequest.setStatus(Status.SUCCESS);
//        logRequest.setChangedFields(changedFields);
//
//        activityLogService.logActivity(userId, logRequest);
//    }
//
//    private String detectAction(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        if (method.isAnnotationPresent(PostMapping.class)) return "CREATE";
//        if (method.isAnnotationPresent(PutMapping.class) || method.isAnnotationPresent(PatchMapping.class))
//            return "UPDATE";
//        if (method.isAnnotationPresent(DeleteMapping.class)) return "DELETE";
//        return "READ";
//    }
//
//    private Map<String, Object> getChangedFields(JoinPoint joinPoint, Object response, String action) {
//        Map<String, Object> changes = new HashMap<>();
//        Object[] args = joinPoint.getArgs();
//
//        if ("authenticate".equalsIgnoreCase(joinPoint.getSignature().getName()) && args.length > 0) {
//            if (args[0] instanceof AuthenticationRequest authRequest) {
//                changes.put("username", authRequest.getUsername());
//                changes.put("password", hashPassword(authRequest.getPassword()));
//            }
//            return changes;
//        }
//
//        if ("READ".equals(action)) {
//            changes.put("parameters", args);
//        } else if ("CREATE".equals(action) || "UPDATE".equals(action)) {
//            changes.put("parameters", args);
//            changes.put("after", extractResponseData(response));
//        } else if ("DELETE".equals(action)) {
//            if (args.length > 0) {
//                changes.put("before", getExistingData(args));
//            }
//        }
//
//        return changes;
//    }
//
//    private Object extractResponseData(Object response) {
//        if (response instanceof ResponseEntity<?> entity) {
//            return entity.getBody();
//        }
//        return response;
//    }
//
//    private Object getExistingData(Object[] args) {
//        if (args.length == 0) return null;
//
//        Object entity = args[0];
//        if (entity instanceof Long id) {
//            Optional<User> user = userRepository.findById(id);
//            return user.map(User::toString).orElse("Not Found");
//        }
//        return entity;
//    }
//
//    private Long getUserIdFromSecurityContext() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//            Map<String, Object> claims = jwtAuth.getTokenAttributes();
//            return ((Number) claims.get("user_id")).longValue();
//        }
//        return 1L;
//    }
//
//    private String hashPassword(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }
// }

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoggingAspect {

    ActivityLogService activityLogService;
    UserRepository userRepository;
    ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* com.hacof.identity.controller.*Controller.*(..))", returning = "result")
    public void logAfterControllerMethods(JoinPoint joinPoint, Object result) {
        try {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String ipAddress = getClientIpAddress(request);
            String deviceDetails = request.getHeader("User-Agent");

            Long userId = getCurrentUserId();
            if (userId == null) {
                log.warn("No authenticated user found for logging activity");
                return;
            }

            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            String action = determineAction(method);
            if (action == null) {
                log.warn("Could not determine action type for method: {}", method.getName());
                return;
            }

            String target = determineTarget(method, joinPoint.getArgs());

            StringBuilder logInfoBuilder = new StringBuilder();
            logInfoBuilder.append("{\"request\": ");

            try {
                String requestJson = objectMapper.writeValueAsString(extractRequestData(joinPoint, method));
                logInfoBuilder.append(requestJson);
            } catch (Exception e) {
                logInfoBuilder.append("\"Error extracting request data\"");
                log.error("Error extracting request data", e);
            }

            logInfoBuilder.append(", \"response\": ");

            try {
                if (result != null) {
                    String responseJson = objectMapper.writeValueAsString(result);
                    logInfoBuilder.append(responseJson);
                } else {
                    logInfoBuilder.append("null");
                }
            } catch (Exception e) {
                logInfoBuilder.append("\"Error extracting response data\"");
                log.error("Error extracting response data", e);
            }

            logInfoBuilder.append("}");

            ActivityLogRequest logRequest = ActivityLogRequest.builder()
                    .action(action)
                    .target(target)
                    .changedFields(logInfoBuilder.toString())
                    .status(Status.SUCCESS)
                    .ipAddress(ipAddress)
                    .deviceDetails(deviceDetails)
                    .build();

            activityLogService.logActivity(userId, logRequest);

        } catch (Exception e) {
            log.error("Error while logging controller activity", e);
        }
    }

    private Map<String, Object> extractRequestData(JoinPoint joinPoint, Method method) {
        Map<String, Object> requestData = new HashMap<>();

        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] != null && isLoggableParameter(parameterNames[i], args[i])) {
                requestData.put(parameterNames[i], args[i]);
            }
        }

        return requestData;
    }

    private boolean isLoggableParameter(String paramName, Object value) {
        if (value == null
                || paramName.toLowerCase().contains("password")
                || paramName.toLowerCase().contains("token")
                || paramName.toLowerCase().contains("secret")) {
            return false;
        }

        if (value.getClass().isPrimitive()
                || value instanceof String
                || value instanceof Number
                || value instanceof Boolean) {
            return true;
        }

        try {
            String json = objectMapper.writeValueAsString(value);
            return json.length() < 10000;
        } catch (Exception e) {
            return false;
        }
    }

    private String determineAction(Method method) {
        if (method.isAnnotationPresent(PostMapping.class)
                || method.isAnnotationPresent(PutMapping.class)
                        && method.getName().startsWith("create")) {
            return "CREATE";
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(PutMapping.class) || method.isAnnotationPresent(PatchMapping.class)) {
            return "UPDATE";
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return "DELETE";
        }

        String methodName = method.getName().toLowerCase();
        if (methodName.startsWith("get") || methodName.startsWith("find") || methodName.startsWith("search")) {
            return "GET";
        } else if (methodName.startsWith("create") || methodName.startsWith("add")) {
            return "CREATE";
        } else if (methodName.startsWith("update")
                || methodName.startsWith("modify")
                || methodName.startsWith("edit")) {
            return "UPDATE";
        } else if (methodName.startsWith("delete") || methodName.startsWith("remove")) {
            return "DELETE";
        }

        return null;
    }

    private String determineTarget(Method method, Object[] args) {
        for (int i = 0; i < method.getParameters().length; i++) {
            PathVariable pathVariable = method.getParameters()[i].getAnnotation(PathVariable.class);
            if (pathVariable != null && args[i] != null) {
                return method.getDeclaringClass().getSimpleName() + " ID: " + args[i].toString();
            }
        }

        return method.getDeclaringClass().getSimpleName().replace("Controller", "");
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> userOptional = userRepository.findByUsername(username);
            return userOptional.map(User::getId).orElse(null);
        }
        return null;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
