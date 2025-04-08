package com.hacof.hackathon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hacof.hackathon.dto.ApiResponse;
import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.mapper.FileUrlMapper;
import com.hacof.hackathon.repository.FileUrlRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.hackathon.service.impl.S3Service;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UploadController {

    S3Service s3Service;
    FileUrlMapper fileUrlMapper;
    FileUrlRepository fileUrlRepository;

    // use to upload Hackathon banner image
    @PostMapping
    public ResponseEntity<CommonResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = s3Service.uploadFile(file);
        log.debug("Image uploaded to S3: {}", imageUrl);
        CommonResponse<String> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Image uploaded successfully"), imageUrl);
        return ResponseEntity.ok(response);
    }

    // pending - not use
    @PostMapping("/multiple")
    public ResponseEntity<CommonResponse<List<String>>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files) {
        List<String> fileUrls = s3Service.uploadFiles(files);
        log.debug("Files uploaded to S3: {}", fileUrls);
        CommonResponse<List<String>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Files uploaded successfully"), fileUrls);
        return ResponseEntity.ok(response);
    }

    // getAllFiles
    @GetMapping
    public ResponseEntity<CommonResponse<List<String>>> getAllFiles() {
        List<String> fileUrls = s3Service.getAllFiles();
        log.debug("Retrieved all files from S3: {}", fileUrls);
        CommonResponse<List<String>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Files retrieved successfully"), fileUrls);
        return ResponseEntity.ok(response);
    }

    // use to upload files to S3 and save the file URL in the database - link to Hackathon
    @PostMapping("/upload")
    public ApiResponse<List<FileUrlResponse>> uploadFiles(@RequestParam("files") List<MultipartFile> files)
            throws IOException {
        List<FileUrlResponse> fileUrlResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            String contentType = file.getContentType();

            InputStream inputStream = file.getInputStream();

            String fileUrl = s3Service.uplFile(inputStream, originalFileName, fileSize, contentType);

            FileUrl fileUrlEntity = new FileUrl();
            fileUrlEntity.setFileName(originalFileName);
            fileUrlEntity.setFileUrl(fileUrl);
            fileUrlEntity.setFileType(contentType);
            fileUrlEntity.setFileSize((int) fileSize);

            fileUrlRepository.save(fileUrlEntity);

            FileUrlResponse fileUrlResponse = fileUrlMapper.toResponse(fileUrlEntity);

            fileUrlResponses.add(fileUrlResponse);
        }

        return ApiResponse.<List<FileUrlResponse>>builder()
                .message("Files uploaded successfully")
                .data(fileUrlResponses)
                .build();
    }
}
