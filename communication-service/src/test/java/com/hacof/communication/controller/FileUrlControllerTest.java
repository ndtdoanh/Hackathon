package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.service.FileUrlService;
import com.hacof.communication.util.CommonResponse;

class FileUrlControllerTest {

    @Mock
    private FileUrlService fileUrlService;

    @InjectMocks
    private FileUrlController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteFile_Success() {
        String fileId = "file-123";
        ResponseEntity<CommonResponse<String>> response = controller.deleteFile(fileId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("File deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteFile_IllegalArgumentException() {
        String fileId = "invalid-file";
        doThrow(new IllegalArgumentException("File not found"))
                .when(fileUrlService)
                .deleteFileById(fileId);

        ResponseEntity<CommonResponse<String>> response = controller.deleteFile(fileId);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("File not found", response.getBody().getMessage());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testDeleteFile_Exception() {
        String fileId = "crash-file";
        doThrow(new RuntimeException("Something went wrong"))
                .when(fileUrlService)
                .deleteFileById(fileId);

        ResponseEntity<CommonResponse<String>> response = controller.deleteFile(fileId);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Something went wrong", response.getBody().getMessage());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }
}
