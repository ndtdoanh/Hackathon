package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.request.BulkBoardListUpdateRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.service.BoardListService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class BoardListControllerTest {

    @Mock
    private BoardListService boardListService;

    @InjectMocks
    private BoardListController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<BoardListRequestDTO> buildRequest() {
        CommonRequest<BoardListRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new BoardListRequestDTO());
        return request;
    }

    private CommonRequest<BoardListRequestDTO> buildRequestWithNullFields() {
        CommonRequest<BoardListRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new BoardListRequestDTO());
        return request;
    }

    @Test
    void testCreateBoardList_CommonFieldNull_Coverage() {
        when(boardListService.createBoardList(any())).thenReturn(new BoardListResponseDTO());

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response =
                controller.createBoardList(buildRequestWithNullFields());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreateBoardList_Success() {
        when(boardListService.createBoardList(any())).thenReturn(new BoardListResponseDTO());

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.createBoardList(buildRequest());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateBoardList_IllegalArgument() {
        when(boardListService.createBoardList(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.createBoardList(buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateBoardList_Exception() {
        when(boardListService.createBoardList(any())).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.createBoardList(buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Crash"));
    }

    @Test
    void testUpdateBoardList_Success() {
        when(boardListService.updateBoardList(eq(1L), any())).thenReturn(new BoardListResponseDTO());

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.updateBoardList(1L, buildRequest());

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUpdateBoardList_IllegalArgument() {
        when(boardListService.updateBoardList(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.updateBoardList(1L, buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateBoardList_Exception() {
        when(boardListService.updateBoardList(eq(1L), any())).thenThrow(new RuntimeException("Server error"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.updateBoardList(1L, buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Server error"));
    }

    @Test
    void testDeleteBoardList_Success() {
        doNothing().when(boardListService).deleteBoardList(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardList(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Board List deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardList_IllegalArgument() {
        doThrow(new IllegalArgumentException("Not found"))
                .when(boardListService)
                .deleteBoardList(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardList(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardList_Exception() {
        doThrow(new RuntimeException("Unexpected error")).when(boardListService).deleteBoardList(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteBoardList(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Unexpected error"));
    }

    @Test
    void testGetBoardList_Success() {
        when(boardListService.getBoardList(1L)).thenReturn(new BoardListResponseDTO());

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.getBoardList(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetBoardList_IllegalArgument() {
        when(boardListService.getBoardList(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.getBoardList(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testGetBoardList_Exception() {
        when(boardListService.getBoardList(1L)).thenThrow(new RuntimeException("Oops"));

        ResponseEntity<CommonResponse<BoardListResponseDTO>> response = controller.getBoardList(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Oops"));
    }

    @Test
    void testGetAllBoardLists_Success() {
        when(boardListService.getAllBoardLists()).thenReturn(List.of(new BoardListResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.getAllBoardLists();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllBoardLists_Exception() {
        when(boardListService.getAllBoardLists()).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.getAllBoardLists();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("DB error"));
    }

    @Test
    void testGetBoardListsByBoardId_Success() {
        when(boardListService.getBoardListByBoardId(999L)).thenReturn(List.of(new BoardListResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.getBoardListByBoardId(999L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetBoardListsByBoardId_Exception() {
        when(boardListService.getBoardListByBoardId(999L)).thenThrow(new RuntimeException("Board failed"));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.getBoardListByBoardId(999L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Board failed"));
    }

    @Test
    void testUpdateBulkBoardLists_Success() {
        CommonRequest<List<BulkBoardListUpdateRequestDTO>> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(Collections.emptyList());

        when(boardListService.updateBulkBoardLists(any())).thenReturn(List.of(new BoardListResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.updateBulkBoardLists(request);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testUpdateBulkBoardLists_IllegalArgument() {
        CommonRequest<List<BulkBoardListUpdateRequestDTO>> request = new CommonRequest<>();
        request.setData(Collections.emptyList());

        when(boardListService.updateBulkBoardLists(any())).thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.updateBulkBoardLists(request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void testUpdateBulkBoardLists_Exception() {
        CommonRequest<List<BulkBoardListUpdateRequestDTO>> request = new CommonRequest<>();
        request.setData(Collections.emptyList());

        when(boardListService.updateBulkBoardLists(any())).thenThrow(new RuntimeException("Update failed"));

        ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> response = controller.updateBulkBoardLists(request);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Update failed"));
    }
}
