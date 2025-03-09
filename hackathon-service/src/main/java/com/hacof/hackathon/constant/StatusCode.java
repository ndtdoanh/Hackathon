package com.hacof.hackathon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    SUCCESS("0000", "Success"),
    TIMEOUT("6800", "Timeout"),
    ERROR("0600", "Error"),
    INVALID_INPUT("0310", "Invalid Input Data"),
    NOT_FOUND("1458", "Not Found");

    private final String code;
    private final String description;
}
