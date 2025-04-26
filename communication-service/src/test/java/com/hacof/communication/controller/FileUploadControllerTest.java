package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.mapper.FileUrlMapper;
import com.hacof.communication.repository.FileUrlRepository;
import com.hacof.communication.service.impl.S3Service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class FileUploadControllerTest {

    @InjectMocks
    FileUploadController fileUploadController;

    @Mock
    S3Service s3Service;

    @Mock
    FileUrlRepository fileUrlRepository;

    @Mock
    FileUrlMapper fileUrlMapper;

    @Test
    void testUploadFiles() throws IOException {
        MultipartFile mockFile = new MockMultipartFile("file", "testFile.txt", "text/plain", "test content".getBytes());

        String mockFileUrl = "https://mockbucket.s3.amazonaws.com/testFile.txt";
        FileUrl mockFileUrlEntity = new FileUrl();
        mockFileUrlEntity.setFileName("testFile.txt");
        mockFileUrlEntity.setFileUrl(mockFileUrl);
        mockFileUrlEntity.setFileType("text/plain");
        mockFileUrlEntity.setFileSize(100);

        FileUrlResponse mockFileUrlResponse = new FileUrlResponse();
        mockFileUrlResponse.setFileName("testFile.txt");
        mockFileUrlResponse.setFileUrl(mockFileUrl);

        List<MultipartFile> files = new ArrayList<>();
        files.add(mockFile);

        when(s3Service.uploadFile(any(), anyString(), anyLong(), anyString())).thenReturn(mockFileUrl);
        when(fileUrlRepository.save(any(FileUrl.class))).thenReturn(mockFileUrlEntity);
        when(fileUrlMapper.toResponse(any(FileUrl.class))).thenReturn(mockFileUrlResponse);

        ApiResponse<List<FileUrlResponse>> apiResponse = fileUploadController.uploadFiles(files);

        assertNotNull(apiResponse);
        assertEquals("Files uploaded successfully", apiResponse.getMessage());
        assertEquals(1000, apiResponse.getCode());
        assertNotNull(apiResponse.getData());
        assertEquals(1, apiResponse.getData().size());
        assertEquals("testFile.txt", apiResponse.getData().get(0).getFileName());
        assertEquals(mockFileUrl, apiResponse.getData().get(0).getFileUrl());
        verify(s3Service, times(1)).uploadFile(any(), anyString(), anyLong(), anyString());
        verify(fileUrlRepository, times(1)).save(any(FileUrl.class));
        verify(fileUrlMapper, times(1)).toResponse(any(FileUrl.class));
    }
}
