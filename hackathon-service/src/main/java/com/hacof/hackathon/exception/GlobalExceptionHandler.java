package com.hacof.hackathon.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.util.CommonResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<CommonResponse<Void>> handleInvalidInputException(InvalidInputException ex) {
        return buildResponseEntity(StatusCode.INVALID_INPUT, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericErrorException.class)
    public ResponseEntity<CommonResponse<Void>> handleGenericErrorException(GenericErrorException ex) {
        return buildResponseEntity(StatusCode.ERROR, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponseEntity(StatusCode.NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<CommonResponse<Void>> handleTimeoutException(TimeoutException ex) {
        return buildResponseEntity(StatusCode.TIMEOUT, ex.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(CustomException ex) {
        return buildResponseEntity(ex.getStatusCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleGenericExceptions(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildResponseEntity(StatusCode.ERROR, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<CommonResponse<Void>> buildResponseEntity(
            StatusCode statusCode, String message, HttpStatus httpStatus) {
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(statusCode.getCode(), message),
                null);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
