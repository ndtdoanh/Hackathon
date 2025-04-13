package com.hacof.communication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;
import com.hacof.communication.service.FileUrlService;
import java.util.UUID;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/file-urls")
public class FileUrlController {

    @Autowired
    private FileUrlService fileUrlService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(request.getRequestId() != null ? request.getRequestId() : UUID.randomUUID().toString());
        response.setRequestDateTime(request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteFile(@PathVariable String id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            fileUrlService.deleteFileById(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("File deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
