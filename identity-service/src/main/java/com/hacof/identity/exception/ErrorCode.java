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
    ASSIGNED_ROLE_IS_REQUIRED(1008, "Assigned role is required", HttpStatus.BAD_REQUEST),
    INVALID_ASSIGNED_ROLE(1009, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(1010, "Invalid token", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1011, "Invalid credentials, please try again.", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1012, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1013, "You do not have permission", HttpStatus.FORBIDDEN),
    PERMISSION_NOT_IN_ROLE(1014, "Permission not in role", HttpStatus.BAD_REQUEST),
    DEVICE_EXISTED(1015, "Device existed", HttpStatus.BAD_REQUEST),
    DEVICE_NOT_EXISTED(1016, "Device not existed", HttpStatus.NOT_FOUND),
    USER_DEVICE_NOT_EXISTED(1017, "User device not existed", HttpStatus.NOT_FOUND),
    INVALID_EMAIL_FORMAT(1018, "Invalid email format", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VERIFIED(1019, "Email is not verified", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1020, "OTP is incorrect or has expired", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(1021, "New password and confirmation password do not match", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD(1022, "Current password is incorrect", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_SAME_AS_OLD(1023, "New password must not be the same as the old password", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(1024, "Invalid file type. Only JPEG and PNG are allowed.", HttpStatus.BAD_REQUEST),
    ROLE_ID_IS_REQUIRED(1025, "RoleId is required", HttpStatus.BAD_REQUEST),
    HACKATHON_NOT_FOUND(1026, "Hackathon not found", HttpStatus.NOT_FOUND),
    ROUND_NOT_FOUND(1027, "Round not found", HttpStatus.NOT_FOUND),
    ROUND_LOCATION_NOT_FOUND(1028, "Round location not found", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED(1029, "File upload failed", HttpStatus.BAD_REQUEST),
    USER_DEVICE_TRACK_NOT_EXISTED(1030, "User device track not existed", HttpStatus.BAD_REQUEST),
    DEVICE_NOT_FOUND(1031, "Device not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
