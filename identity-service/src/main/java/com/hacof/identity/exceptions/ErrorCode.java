package com.hacof.identity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_IS_REQUIRED(1002, "Email is required", HttpStatus.BAD_REQUEST),
    EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS(1003, "Email must be a valid Gmail address", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_REQUIRED(1004, "Password is required", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password invalid", HttpStatus.BAD_REQUEST),
    INVALID_ASSIGNED_ROLE(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_ROLE(1007, "Invalid role", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1008, "Invalid token", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1009, "User not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(1010, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(1011, "Permission not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1012, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1013, "You do not have permission", HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
