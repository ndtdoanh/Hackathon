package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.service.FileUrlService;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/file-urls")
public class FileUrlController {

    @Autowired
    private FileUrlService fileUrlService;

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
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("File deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @GetMapping("/media")
    public ResponseEntity<CommonResponse<List<FileUrlResponse>>> getMediaFiles() {
        CommonResponse<List<FileUrlResponse>> response = new CommonResponse<>();
        try {
            String prefix = "mediaGallery";
            List<FileUrlResponse> files = fileUrlService.getFilesStartingWith(prefix);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Files retrieved successfully!");
            response.setData(files);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
