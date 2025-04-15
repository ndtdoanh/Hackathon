package com.hacof.hackathon.test;

import com.hacof.hackathon.controller.UploadController;
import com.hacof.hackathon.service.impl.S3Service;
import com.hacof.hackathon.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UploadControllerTest {

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private UploadController uploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadImage() {
        MultipartFile file = mock(MultipartFile.class);
        when(s3Service.uploadFile(file)).thenReturn("http://s3.amazonaws.com/image.jpg");

        ResponseEntity<CommonResponse<String>> response = uploadController.uploadImage(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("http://s3.amazonaws.com/image.jpg", response.getBody().getData());
        verify(s3Service, times(1)).uploadFile(file);
    }

    @Test
    void testGetAllFiles() {
        when(s3Service.getAllFiles()).thenReturn(Collections.singletonList("http://s3.amazonaws.com/image.jpg"));

        ResponseEntity<CommonResponse<List<String>>> response = uploadController.getAllFiles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(s3Service, times(1)).getAllFiles();
    }
}