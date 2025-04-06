package com.hacof.hackathon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
