package com.hacof.identity.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hacof.identity.dtos.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHanler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiResponse apiResponse = new ApiResponse();

        List<String> notBlankErrors = exception.getBindingResult().getFieldErrors().stream()
                .filter(error -> "NotBlank".equals(error.getCode()))
                .map(error -> error.getField())
                .collect(Collectors.toList());

        if (!notBlankErrors.isEmpty()) {
            if (notBlankErrors.contains("email")) {
                apiResponse.setCode(ErrorCode.EMAIL_IS_REQUIRED.getCode());
                apiResponse.setMessage(ErrorCode.EMAIL_IS_REQUIRED.getMessage());
            } else if (notBlankErrors.contains("password")) {
                apiResponse.setCode(ErrorCode.PASSWORD_IS_REQUIRED.getCode());
                apiResponse.setMessage(ErrorCode.PASSWORD_IS_REQUIRED.getMessage());
            }
            return ResponseEntity.badRequest().body(apiResponse);
        }

        List<FieldError> emailErrors = exception.getBindingResult().getFieldErrors().stream()
                .filter(error -> "email".equals(error.getField()))
                .collect(Collectors.toList());

        if (!emailErrors.isEmpty()) {
            apiResponse.setCode(ErrorCode.EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS.getCode());
            apiResponse.setMessage(ErrorCode.EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        List<FieldError> passwordErrors = exception.getBindingResult().getFieldErrors().stream()
                .filter(error -> "password".equals(error.getField()))
                .collect(Collectors.toList());

        if (!passwordErrors.isEmpty()) {
            apiResponse.setCode(ErrorCode.PASSWORD_INVALID.getCode());
            apiResponse.setMessage(ErrorCode.PASSWORD_INVALID.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
