package com.hacof.submission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.submission.controller.JudgeRoundController;
import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.service.JudgeRoundService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;

class JudgeRoundControllerTest {

    @Mock
    private JudgeRoundService service;

    @InjectMocks
    private JudgeRoundController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<JudgeRoundRequestDTO> buildRequest() {
        JudgeRoundRequestDTO dto = new JudgeRoundRequestDTO();
        CommonRequest<JudgeRoundRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);
        return request;
    }

    @Test
    void testCreateJudgeRound() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        JudgeRoundResponseDTO responseDTO = new JudgeRoundResponseDTO();
        when(service.createJudgeRound(any())).thenReturn(responseDTO);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.createJudgeRound(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateJudgeRound_IllegalArgumentException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.createJudgeRound(any())).thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.createJudgeRound(request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void testCreateJudgeRound_GenericException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.createJudgeRound(any())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.createJudgeRound(request);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unexpected error"));
    }

    @Test
    void testCreateJudgeRound_WithMissingFieldsInRequest() {
        CommonRequest<JudgeRoundRequestDTO> request = new CommonRequest<>();
        request.setData(new JudgeRoundRequestDTO());

        JudgeRoundResponseDTO mockResponse = new JudgeRoundResponseDTO();
        when(service.createJudgeRound(any())).thenReturn(mockResponse);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.createJudgeRound(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testUpdateJudgeRound_Success() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        JudgeRoundResponseDTO responseDTO = new JudgeRoundResponseDTO();
        when(service.updateJudgeRound(eq(1L), any())).thenReturn(responseDTO);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.updateJudgeRound(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateJudgeRound_NotFound() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.updateJudgeRound(eq(1L), any())).thenReturn(null);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.updateJudgeRound(1L, request);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody().getData());
    }

    @Test
    void testUpdateJudgeRound_IllegalArgumentException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.updateJudgeRound(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.updateJudgeRound(1L, request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateJudgeRound_GenericException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.updateJudgeRound(eq(1L), any())).thenThrow(new RuntimeException("Something failed"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.updateJudgeRound(1L, request);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Something failed"));
    }

    @Test
    void testDeleteJudgeRound_Success() {
        when(service.deleteJudgeRound(1L)).thenReturn(true);

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeRound(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertTrue(response.getBody().getData());
    }

    @Test
    void testDeleteJudgeRound_NotFound() {
        when(service.deleteJudgeRound(1L)).thenReturn(false);

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeRound(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody().getData());
    }

    @Test
    void testDeleteJudgeRound_GenericException() {
        when(service.deleteJudgeRound(1L)).thenThrow(new RuntimeException("Unexpected deletion error"));

        ResponseEntity<CommonResponse<Boolean>> response = controller.deleteJudgeRound(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unexpected deletion error"));
        assertNull(response.getBody().getData());
    }

    @Test
    void testGetJudgeRound_Success() {
        JudgeRoundResponseDTO dto = new JudgeRoundResponseDTO();
        when(service.getJudgeRound(1L)).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.getJudgeRound(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetJudgeRound_Empty() {
        when(service.getJudgeRound(1L)).thenReturn(null);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.getJudgeRound(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody().getData());
    }

    @Test
    void testGetJudgeRound_IllegalArgumentException() {
        when(service.getJudgeRound(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.getJudgeRound(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Not found"));
    }

    @Test
    void testGetJudgeRound_GenericException() {
        when(service.getJudgeRound(1L)).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response = controller.getJudgeRound(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unexpected"));
    }

    @Test
    void testGetAllJudgeRounds() {
        when(service.getAllJudgeRounds()).thenReturn(Collections.singletonList(new JudgeRoundResponseDTO()));

        ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> response = controller.getAllJudgeRounds();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testGetAllJudgeRounds_GenericException() {
        when(service.getAllJudgeRounds()).thenThrow(new RuntimeException("Database unreachable"));

        ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> response = controller.getAllJudgeRounds();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Database unreachable"));
        assertNull(response.getBody().getData());
    }

    @Test
    void testUpdateByJudgeId() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        JudgeRoundResponseDTO dto = new JudgeRoundResponseDTO();
        when(service.updateJudgeRoundByJudgeId(eq(2L), any())).thenReturn(dto);

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response =
                controller.updateJudgeRoundByJudgeId(2L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateByJudgeId_IllegalArgumentException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.updateJudgeRoundByJudgeId(eq(5L), any()))
                .thenThrow(new IllegalArgumentException("Invalid judgeId"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response =
                controller.updateJudgeRoundByJudgeId(5L, request);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Invalid judgeId"));
    }

    @Test
    void testUpdateByJudgeId_GenericException() {
        CommonRequest<JudgeRoundRequestDTO> request = buildRequest();
        when(service.updateJudgeRoundByJudgeId(eq(5L), any())).thenThrow(new RuntimeException("DB failure"));

        ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> response =
                controller.updateJudgeRoundByJudgeId(5L, request);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("DB failure"));
    }

    @Test
    void testGetByRoundId_WithData() {
        when(service.getJudgeRoundsByRoundId(3L)).thenReturn(Collections.singletonList(new JudgeRoundResponseDTO()));

        ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> response = controller.getJudgeRoundsByRoundId(3L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetByRoundId_Empty() {
        when(service.getJudgeRoundsByRoundId(3L)).thenReturn(Collections.emptyList());

        ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> response = controller.getJudgeRoundsByRoundId(3L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetByRoundId_GenericException() {
        when(service.getJudgeRoundsByRoundId(8L)).thenThrow(new RuntimeException("Service error"));

        ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> response = controller.getJudgeRoundsByRoundId(8L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Service error"));
    }

    @Test
    void testDeleteByJudgeIdAndRoundId() {
        doNothing().when(service).deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("JudgeRound deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteByJudgeIdAndRoundId_IllegalArgumentException() {
        doThrow(new IllegalArgumentException("Invalid IDs"))
                .when(service)
                .deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid IDs", response.getBody().getMessage());
    }

    @Test
    void testDeleteByJudgeIdAndRoundId_GenericException() {
        doThrow(new RuntimeException("Unexpected error")).when(service).deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteJudgeRoundByJudgeIdAndRoundId(10L, 20L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unexpected error"));
    }
}
