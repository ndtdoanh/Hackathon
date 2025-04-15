package com.hacof.hackathon.exception;

import com.hacof.hackathon.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final StatusCode statusCode;

    public CustomException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
