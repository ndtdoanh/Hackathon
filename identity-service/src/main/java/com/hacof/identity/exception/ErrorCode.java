package com.hacof.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1002, "Role existed", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1003, "Permission existed", HttpStatus.BAD_REQUEST),
    PASSWORD_EXISTED(1004, "Password existed", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(1006, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(1007, "Permission not existed", HttpStatus.NOT_FOUND),

    USERNAME_IS_REQUIRED(1008, "Username is required", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_REQUIRED(1009, "Password is required", HttpStatus.BAD_REQUEST),
    ASSIGNED_ROLE_IS_REQUIRED(1010, "Assigned role is required", HttpStatus.BAD_REQUEST),

    INVALID_PASSWORD(1011, "Password invalid", HttpStatus.BAD_REQUEST),
    INVALID_ASSIGNED_ROLE(1012, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_ROLE(1013, "Invalid role", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1014, "Invalid token", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1015, "Invalid credentials, please try again.", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1016, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1017, "You do not have permission", HttpStatus.FORBIDDEN),

    PROFILE_NOT_FOUND(1018, "Profile not found", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS(1019, "Profile already exists for this user", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_PROFILE_ACCESS(1020, "You are not authorized to access this profile", HttpStatus.FORBIDDEN),
    INVALID_FILE_FORMAT(1021, "Only JPG and PNG files are allowed", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_ERROR(1022, "Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR),

    PERMISSION_NOT_IN_ROLE(1023, "Permission not in role", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
