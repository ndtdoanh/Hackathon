package com.hacof.hackathon.exception;

import com.hacof.hackathon.constant.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
