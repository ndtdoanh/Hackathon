package com.hacof.identity.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized"),
    USER_EXISTED(1002, "User existed"),
    EMAIL_IS_REQUIRED(1003, "Email is required"),
    EMAIL_MUST_BE_A_VALID_GMAIL_ADDRESS(1004, "Email must be a valid Gmail address"),
    PASSWORD_IS_REQUIRED(1005, "Password is required"),
    PASSWORD_INVALID(1006, "Password invalid"),
    USER_NOT_EXISTED(1007, "User not existed"),
    UNAUTHENTICATED(1008, "Unauthenticated");

    private int code;
    private String message;
}
