package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.hackathon.service.impl.S3Service;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final S3Service s3Service;

    @PostMapping("/image")
    public ResponseEntity<CommonResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = s3Service.uploadFile(file);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Image uploaded successfully"),
                imageUrl));
    }
}
