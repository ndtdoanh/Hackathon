package com.hacof.identity.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_IS_REQUIRED(1002, "Email is required", HttpStatus.BAD_REQUEST),
    EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS(1003, "Email must be a valid Gmail address", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_REQUIRED(1004, "Password is required", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password invalid", HttpStatus.BAD_REQUEST),
    INVALID_ASSIGNED_ROLE(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_ROLE(1007, "Invalid role", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1008, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1009, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1010, "You do not have permission", HttpStatus.FORBIDDEN),

    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
