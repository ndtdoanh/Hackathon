package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.service.BoardLabelService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class BoardLabelControllerTest {

    @Mock
    private BoardLabelService boardLabelService;

    @InjectMocks
    private BoardLabelController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<BoardLabelRequestDTO> buildRequest() {
        CommonRequest<BoardLabelRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new BoardLabelRequestDTO());
        return request;
    }

    private CommonRequest<BoardLabelRequestDTO> buildRequestWithNullFields() {
        CommonRequest<BoardLabelRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new BoardLabelRequestDTO());
        return request;
    }

    @Test
    void testCreateBoardLabel_AllFieldsNull_CoverageSetCommonResponseFields() {
        when(boardLabelService.createBoardLabel(any())).thenReturn(new BoardLabelResponseDTO());

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response =
                controller.createBoardLabel(buildRequestWithNullFields());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreateBoardLabel_Success() {
        when(boardLabelService.createBoardLabel(any())).thenReturn(new BoardLabelResponseDTO());

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.createBoardLabel(buildRequest());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateBoardLabel_IllegalArgument() {
        when(boardLabelService.createBoardLabel(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.createBoardLabel(buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateBoardLabel_Exception() {
        when(boardLabelService.createBoardLabel(any())).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.createBoardLabel(buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Crash"));
    }

    @Test
    void testUpdateBoardLabel_Success() {
        when(boardLabelService.updateBoardLabel(eq(1L), any())).thenReturn(new BoardLabelResponseDTO());

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response =
                controller.updateBoardLabel(1L, buildRequest());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBoardLabel_IllegalArgument() {
        when(boardLabelService.updateBoardLabel(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response =
                controller.updateBoardLabel(1L, buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateBoardLabel_Exception() {
        when(boardLabelService.updateBoardLabel(eq(1L), any())).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response =
                controller.updateBoardLabel(1L, buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Crash"));
    }

    @Test
    void testDeleteBoardLabel_Success() {
        doNothing().when(boardLabelService).deleteBoardLabel(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardLabel(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Board Label deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardLabel_IllegalArgument() {
        doThrow(new IllegalArgumentException("Not found"))
                .when(boardLabelService)
                .deleteBoardLabel(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardLabel(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardLabel_Exception() {
        doThrow(new RuntimeException("Error")).when(boardLabelService).deleteBoardLabel(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardLabel(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Error"));
    }

    @Test
    void testGetBoardLabel_Success() {
        when(boardLabelService.getBoardLabel(1L)).thenReturn(new BoardLabelResponseDTO());

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.getBoardLabel(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetBoardLabel_IllegalArgument() {
        when(boardLabelService.getBoardLabel(1L)).thenThrow(new IllegalArgumentException("Missing"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.getBoardLabel(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Missing", response.getBody().getMessage());
    }

    @Test
    void testGetBoardLabel_Exception() {
        when(boardLabelService.getBoardLabel(1L)).thenThrow(new RuntimeException("Server error"));

        ResponseEntity<CommonResponse<BoardLabelResponseDTO>> response = controller.getBoardLabel(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Server error"));
    }

    @Test
    void testGetAllBoardLabels_Success() {
        when(boardLabelService.getAllBoardLabels()).thenReturn(List.of(new BoardLabelResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> response = controller.getAllBoardLabels();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllBoardLabels_Exception() {
        when(boardLabelService.getAllBoardLabels()).thenThrow(new RuntimeException("Failed"));

        ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> response = controller.getAllBoardLabels();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Failed"));
    }

    @Test
    void testGetBoardLabelsByBoardId_Success() {
        when(boardLabelService.getBoardLabelsByBoardId(99L)).thenReturn(List.of(new BoardLabelResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> response = controller.getBoardLabelsByBoardId(99L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetBoardLabelsByBoardId_IllegalArgument() {
        when(boardLabelService.getBoardLabelsByBoardId(99L)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> response = controller.getBoardLabelsByBoardId(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testGetBoardLabelsByBoardId_Exception() {
        when(boardLabelService.getBoardLabelsByBoardId(99L)).thenThrow(new RuntimeException("Oops"));

        ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> response = controller.getBoardLabelsByBoardId(99L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Oops"));
    }
}
