package com.hacof.submission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.ResponseEntity;
import com.hacof.submission.response.CommonResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<CommonResponse<String>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
    CommonResponse<String> response = new CommonResponse<>();
    response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
    response.setMessage("File size exceeds the maximum allowed limit of 15MB.");
    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
  }
}
