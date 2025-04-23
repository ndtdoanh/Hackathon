package com.hacof.hackathon.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.util.CommonResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({
        InvalidInputException.class,
        GenericErrorException.class,
        TimeoutException.class,
        CustomException.class,
        NotificationException.class
    })
    public ResponseEntity<CommonResponse<Void>> handleException(Exception ex) {
        StatusCode statusCode = getStatusByException(ex);

        log.error("Exception: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildResponseEntity(statusCode, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        CommonResponse<Map<String, String>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.INVALID_INPUT.getCode(), "Invalid input"),
                errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<Void>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage(), ex);

        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0601", "Cannot delete resource due to dependent records."),
                null);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleGenericExceptions(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(StatusCode.ERROR, "An unexpected error occurred");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.NOT_FOUND.getCode(), ex.getMessage()),
                null);
        return ResponseEntity.status(404).body(response);
    }

    private StatusCode getStatusByException(Exception ex) {
        if (ex instanceof InvalidInputException) return StatusCode.INVALID_INPUT;
        if (ex instanceof ResourceNotFoundException) return StatusCode.NOT_FOUND;
        if (ex instanceof TimeoutException) return StatusCode.TIMEOUT;
        return StatusCode.ERROR;
    }

    private <T> ResponseEntity<CommonResponse<T>> buildResponseEntity(StatusCode statusCode, String message, T data) {
        CommonResponse<T> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(statusCode.getCode(), message),
                data);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<CommonResponse<Void>> buildResponseEntity(StatusCode statusCode, String message) {
        return buildResponseEntity(statusCode, message, null);
    }
}
