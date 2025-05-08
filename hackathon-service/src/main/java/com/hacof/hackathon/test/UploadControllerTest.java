package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.hackathon.controller.UploadController;
import com.hacof.hackathon.service.impl.S3Service;
import com.hacof.hackathon.util.CommonResponse;

class UploadControllerTest {

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private UploadController uploadController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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
