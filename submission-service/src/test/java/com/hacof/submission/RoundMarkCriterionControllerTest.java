package com.hacof.submission;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.submission.controller.RoundMarkCriterionController;
import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.service.RoundMarkCriterionService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;

class RoundMarkCriterionControllerTest {

    @Mock
    private RoundMarkCriterionService service;

    @InjectMocks
    private RoundMarkCriterionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<RoundMarkCriterionRequestDTO> buildRequestWithData() {
        RoundMarkCriterionRequestDTO dto = new RoundMarkCriterionRequestDTO();
        CommonRequest<RoundMarkCriterionRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);
        return request;
    }

    private CommonRequest<RoundMarkCriterionRequestDTO> buildRequestWithNulls() {
        CommonRequest<RoundMarkCriterionRequestDTO> request = new CommonRequest<>();
        request.setData(new RoundMarkCriterionRequestDTO());
        return request;
    }

    @Test
    void testGetAll_Success() {
        when(service.getAll()).thenReturn(List.of(new RoundMarkCriterionResponseDTO()));

        ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> response = controller.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAll_Exception() {
        when(service.getAll()).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> response = controller.getAll();

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Success() {
        RoundMarkCriterionResponseDTO dto = new RoundMarkCriterionResponseDTO();
        when(service.getById(1L)).thenReturn(Optional.of(dto));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response = controller.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetById_NotFound() {
        when(service.getById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response = controller.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("not found"));
    }

    @Test
    void testGetById_Exception() {
        when(service.getById(1L)).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response = controller.getById(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        CommonRequest<RoundMarkCriterionRequestDTO> request = buildRequestWithData();
        RoundMarkCriterionResponseDTO dto = new RoundMarkCriterionResponseDTO();
        when(service.create(any())).thenReturn(dto);

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response = controller.create(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreate_WithNullRequestFields() {
        CommonRequest<RoundMarkCriterionRequestDTO> request = buildRequestWithNulls();
        RoundMarkCriterionResponseDTO dto = new RoundMarkCriterionResponseDTO();
        when(service.create(any())).thenReturn(dto);

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response = controller.create(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getRequestId());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreate_IllegalArgumentException() {
        when(service.create(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response =
                controller.create(buildRequestWithData());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        when(service.create(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response =
                controller.create(buildRequestWithData());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        when(service.update(eq(1L), any())).thenReturn(new RoundMarkCriterionResponseDTO());

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_IllegalArgumentException() {
        when(service.update(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        when(service.update(eq(1L), any())).thenThrow(new RuntimeException("Server Error"));

        ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(service).delete(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(
                "Round mark criterion deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDelete_IllegalArgumentException() {
        doThrow(new IllegalArgumentException("Not found")).when(service).delete(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        doThrow(new RuntimeException("Delete fail")).when(service).delete(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByRoundId_Success() {
        when(service.getByRoundId(99L)).thenReturn(List.of(new RoundMarkCriterionResponseDTO()));

        ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> response = controller.getByRoundId(99L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetByRoundId_IllegalArgumentException() {
        when(service.getByRoundId(99L)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> response = controller.getByRoundId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByRoundId_Exception() {
        when(service.getByRoundId(99L)).thenThrow(new RuntimeException("Service Down"));

        ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> response = controller.getByRoundId(99L);

        assertEquals(500, response.getStatusCodeValue());
    }
}
