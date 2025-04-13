package com.hacof.communication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.FileUrlService;

@RestController
@RequestMapping("/api/v1/file-urls")
public class FileUrlController {

    @Autowired
    private FileUrlService fileUrlService;

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteFile(@PathVariable String id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            fileUrlService.deleteFileById(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("File deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
