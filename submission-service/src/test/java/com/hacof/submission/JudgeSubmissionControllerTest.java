package com.hacof.submission;

import com.hacof.submission.controller.JudgeSubmissionController;
import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.service.JudgeSubmissionService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JudgeSubmissionControllerTest {

    @Mock
    private JudgeSubmissionService service;

    @InjectMocks
    private JudgeSubmissionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<JudgeSubmissionRequestDTO> buildRequestWithData() {
        JudgeSubmissionRequestDTO dto = new JudgeSubmissionRequestDTO();
        CommonRequest<JudgeSubmissionRequestDTO> request = new CommonRequest<>();
        request.setData(dto);
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        return request;
    }

    private CommonRequest<JudgeSubmissionRequestDTO> buildRequestNullFields() {
        CommonRequest<JudgeSubmissionRequestDTO> request = new CommonRequest<>();
        request.setData(new JudgeSubmissionRequestDTO());
        return request;
    }

    @Test
    void testCreate_Success() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        JudgeSubmissionResponseDTO dto = new JudgeSubmissionResponseDTO();
        when(service.createJudgeSubmission(any())).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.createJudgeSubmission(request);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreate_IllegalArgumentException() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        when(service.createJudgeSubmission(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.createJudgeSubmission(request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        when(service.createJudgeSubmission(any())).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.createJudgeSubmission(request);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testCreate_WithNullRequestFields() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestNullFields();
        JudgeSubmissionResponseDTO dto = new JudgeSubmissionResponseDTO();
        when(service.createJudgeSubmission(any())).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.createJudgeSubmission(request);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("HACOF", response.getBody().getChannel());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
    }

    @Test
    void testGetById_Success() {
        JudgeSubmissionResponseDTO dto = new JudgeSubmissionResponseDTO();
        when(service.getJudgeSubmissionById(1L)).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.getJudgeSubmissionById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_IllegalArgumentException() {
        when(service.getJudgeSubmissionById(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.getJudgeSubmissionById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAll_Success() {
        when(service.getAllJudgeSubmissions()).thenReturn(Collections.singletonList(new JudgeSubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getAllJudgeSubmissions();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAll_Exception() {
        when(service.getAllJudgeSubmissions()).thenThrow(new RuntimeException("Internal"));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getAllJudgeSubmissions();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        JudgeSubmissionResponseDTO dto = new JudgeSubmissionResponseDTO();
        when(service.updateJudgeSubmission(eq(1L), any())).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.updateJudgeSubmission(1L, request);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_IllegalArgumentException() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        when(service.updateJudgeSubmission(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.updateJudgeSubmission(1L, request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        CommonRequest<JudgeSubmissionRequestDTO> request = buildRequestWithData();
        when(service.updateJudgeSubmission(eq(1L), any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> response = controller.updateJudgeSubmission(1L, request);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(service).deleteJudgeSubmission(1L);

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeSubmission(1L);
        assertEquals(204, response.getStatusCodeValue());
        assertTrue(response.getBody().getData());
    }

    @Test
    void testDelete_IllegalArgumentException() {
        doThrow(new IllegalArgumentException("Not found")).when(service).deleteJudgeSubmission(1L);

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeSubmission(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        doThrow(new RuntimeException("Unexpected")).when(service).deleteJudgeSubmission(1L);

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeSubmission(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByJudgeId_Success() {
        when(service.getSubmissionsByJudgeId(10L)).thenReturn(Collections.singletonList(new JudgeSubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByJudgeId(10L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByJudgeId_IllegalArgumentException() {
        when(service.getSubmissionsByJudgeId(10L)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByJudgeId(10L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByJudgeId_Exception() {
        when(service.getSubmissionsByJudgeId(10L)).thenThrow(new RuntimeException("Fail"));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByJudgeId(10L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByRoundId_Success() {
        when(service.getSubmissionsByRoundId(100L)).thenReturn(Collections.singletonList(new JudgeSubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByRoundId(100L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByRoundId_IllegalArgumentException() {
        when(service.getSubmissionsByRoundId(100L)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByRoundId(100L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByRoundId_Exception() {
        when(service.getSubmissionsByRoundId(100L)).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> response = controller.getSubmissionsByRoundId(100L);
        assertEquals(500, response.getStatusCodeValue());
    }
}
