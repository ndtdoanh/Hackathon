package com.hacof.hackathon.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
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
        ResourceNotFoundException.class,
        TimeoutException.class,
        CustomException.class
    })
    public ResponseEntity<CommonResponse<Void>> handleException(Exception ex) {
        StatusCode statusCode = getStatusByException(ex);

        log.error("Exception: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildResponseEntity(statusCode, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleGenericExceptions(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(StatusCode.ERROR, "An unexpected error occurred");
    }

    private StatusCode getStatusByException(Exception ex) {
        if (ex instanceof InvalidInputException) return StatusCode.INVALID_INPUT;
        if (ex instanceof ResourceNotFoundException) return StatusCode.NOT_FOUND;
        if (ex instanceof TimeoutException) return StatusCode.TIMEOUT;
        return StatusCode.ERROR;
    }

    private ResponseEntity<CommonResponse<Void>> buildResponseEntity(StatusCode statusCode, String message) {
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(statusCode.getCode(), message),
                null);
        return ResponseEntity.ok(response); // Luôn trả về HTTP 200 OK, chỉ thay đổi StatusCode
    }
}
