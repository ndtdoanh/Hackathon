package com.hacof.hackathon.exception;

import java.util.HashMap;
import java.util.Map;

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
        ResourceNotFoundException.class,
        TimeoutException.class,
        CustomException.class
    })
    public ResponseEntity<CommonResponse<Void>> handleException(Exception ex) {
        StatusCode statusCode = getStatusByException(ex);

        log.error("Exception: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildResponseEntity(statusCode, ex.getMessage());
    }

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
    //    public ResponseEntity<CommonResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException
    // ex) {
    //        List<String> errors = ex.getBindingResult().getAllErrors().stream()
    //                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
    //                .collect(Collectors.toList());
    //
    //        log.error("Validation failed: {}", errors);
    //        return buildResponseEntity(StatusCode.INVALID_INPUT, "Validation failed", errors);
    //    }
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
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result(StatusCode.INVALID_INPUT.getCode(), "Invalid input"), errors);

        return ResponseEntity.badRequest().body(response);
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

    private <T> ResponseEntity<CommonResponse<T>> buildResponseEntity(StatusCode statusCode, String message, T data) {
        CommonResponse<T> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result(statusCode.getCode(), message), data);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<CommonResponse<Void>> buildResponseEntity(StatusCode statusCode, String message) {
        return buildResponseEntity(statusCode, message, null);
    }
}
