package com.hacof.hackathon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.hackathon.service.impl.S3Service;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UploadController {

    S3Service s3Service;

    @PostMapping
    public ResponseEntity<CommonResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = s3Service.uploadFile(file);
        log.debug("Image uploaded to S3: {}", imageUrl);
        CommonResponse<String> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Image uploaded successfully"), imageUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/multiple")
    public ResponseEntity<CommonResponse<List<String>>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files) {
        List<String> fileUrls = s3Service.uploadFiles(files);
        log.debug("Files uploaded to S3: {}", fileUrls);
        CommonResponse<List<String>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Files uploaded successfully"), fileUrls);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<String>>> getAllFiles() {
        List<String> fileUrls = s3Service.getAllFiles();
        log.debug("Retrieved all files from S3: {}", fileUrls);
        CommonResponse<List<String>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Files retrieved successfully"), fileUrls);
        return ResponseEntity.ok(response);
    }
}
