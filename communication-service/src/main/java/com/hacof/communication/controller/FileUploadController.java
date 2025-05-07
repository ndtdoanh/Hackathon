package com.hacof.communication.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.mapper.FileUrlMapper;
import com.hacof.communication.repository.FileUrlRepository;
import com.hacof.communication.service.impl.S3Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUploadController {
    S3Service s3Service;
    FileUrlRepository fileUrlRepository;
    FileUrlMapper fileUrlMapper;

    @PostMapping("/upload")
    public ApiResponse<List<FileUrlResponse>> uploadFiles(@RequestParam("files") List<MultipartFile> files)
            throws IOException {
        List<FileUrlResponse> fileUrlResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            String contentType = file.getContentType();

            InputStream inputStream = file.getInputStream();

            String fileUrl = s3Service.uploadFile(inputStream, originalFileName, fileSize, contentType);

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
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Files uploaded successfully")
                .data(fileUrlResponses)
                .build();
    }

    @PostMapping("/upload/media")
    public ApiResponse<List<FileUrlResponse>> uploadMediaToGallery(@RequestParam("files") List<MultipartFile> files)
            throws IOException {
        List<FileUrlResponse> fileUrlResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            String contentType = file.getContentType();

            InputStream inputStream = file.getInputStream();

            String fileUrl = s3Service.uploadFile(inputStream, originalFileName, fileSize, contentType);

            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            FileUrl fileUrlEntity = new FileUrl();
            fileUrlEntity.setFileName(fileName);
            fileUrlEntity.setFileUrl(fileUrl);
            fileUrlEntity.setFileType(contentType);
            fileUrlEntity.setFileSize((int) fileSize);

            fileUrlRepository.save(fileUrlEntity);

            FileUrlResponse fileUrlResponse = fileUrlMapper.toResponse(fileUrlEntity);

            fileUrlResponses.add(fileUrlResponse);
        }

        return ApiResponse.<List<FileUrlResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Files uploaded successfully")
                .data(fileUrlResponses)
                .build();
    }
}
