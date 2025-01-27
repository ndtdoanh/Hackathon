package com.hacof.identity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1002, "Role existed", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1003, "Permission existed", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(1005, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(1006, "Permission not existed", HttpStatus.NOT_FOUND),

    EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS(1007, "Email must be a valid Gmail address", HttpStatus.BAD_REQUEST),
    EMAIL_IS_REQUIRED(1008, "Email is required", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_REQUIRED(1009, "Password is required", HttpStatus.BAD_REQUEST),


    INVALID_PASSWORD(1010, "Password invalid", HttpStatus.BAD_REQUEST),
    INVALID_ASSIGNED_ROLE(1011, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_ROLE(1012, "Invalid role", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1013, "Invalid token", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1014, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1015, "You do not have permission", HttpStatus.FORBIDDEN),
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
