package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.service.BoardService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<BoardRequestDTO> buildRequest() {
        CommonRequest<BoardRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new BoardRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_RequestIdNull_ShouldGenerateUUID() {
        CommonRequest<BoardRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(LocalDateTime.of(2024, 1, 1, 10, 0));
        request.setChannel("HACOF");
        request.setData(new BoardRequestDTO());

        BoardResponseDTO mockResponse = new BoardResponseDTO();
        when(boardService.createBoard(any())).thenReturn(mockResponse);

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(request);

        assertNotNull(response.getBody().getRequestId());
        assertEquals("HACOF", response.getBody().getChannel());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), response.getBody().getRequestDateTime());
    }

    @Test
    void testSetCommonResponseFields_DateTimeNull_ShouldSetNow() {
        CommonRequest<BoardRequestDTO> request = new CommonRequest<>();
        request.setRequestId("test-id");
        request.setRequestDateTime(null);
        request.setChannel("HACOF");
        request.setData(new BoardRequestDTO());

        when(boardService.createBoard(any())).thenReturn(new BoardResponseDTO());

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(request);

        assertNotNull(response.getBody().getRequestDateTime());
    }

    @Test
    void testSetCommonResponseFields_ChannelNull_ShouldSetDefault() {
        CommonRequest<BoardRequestDTO> request = new CommonRequest<>();
        request.setRequestId("abc-123");
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel(null);
        request.setData(new BoardRequestDTO());

        when(boardService.createBoard(any())).thenReturn(new BoardResponseDTO());

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(request);

        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreateBoard_Success() {
        when(boardService.createBoard(any())).thenReturn(new BoardResponseDTO());

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(buildRequest());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateBoard_IllegalArgument() {
        when(boardService.createBoard(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(buildRequest());

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateBoard_Exception() {
        when(boardService.createBoard(any())).thenThrow(new RuntimeException("Internal"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.createBoard(buildRequest());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllBoards_Success() {
        when(boardService.getAllBoards()).thenReturn(List.of(new BoardResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response = boardController.getAllBoards();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllBoards_Exception() {
        when(boardService.getAllBoards()).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response = boardController.getAllBoards();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("DB error"));
    }

    @Test
    void testGetBoardById_Success() {
        when(boardService.getBoard(1L)).thenReturn(new BoardResponseDTO());

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.getBoardById(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardById_IllegalArgument() {
        when(boardService.getBoard(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.getBoardById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardById_Exception() {
        when(boardService.getBoard(1L)).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.getBoardById(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBoard_Success() {
        when(boardService.updateBoard(eq(1L), any())).thenReturn(new BoardResponseDTO());

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.updateBoard(1L, buildRequest());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBoard_IllegalArgument() {
        when(boardService.updateBoard(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.updateBoard(1L, buildRequest());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBoard_Exception() {
        when(boardService.updateBoard(eq(1L), any())).thenThrow(new RuntimeException("Update failed"));

        ResponseEntity<CommonResponse<BoardResponseDTO>> response = boardController.updateBoard(1L, buildRequest());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteBoard_Success() {
        doNothing().when(boardService).deleteBoard(1L);

        ResponseEntity<CommonResponse<String>> response = boardController.deleteBoard(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Board deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoard_IllegalArgument() {
        doThrow(new IllegalArgumentException("Board not found"))
                .when(boardService)
                .deleteBoard(1L);

        ResponseEntity<CommonResponse<String>> response = boardController.deleteBoard(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Board not found", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoard_Exception() {
        doThrow(new RuntimeException("Deletion failed")).when(boardService).deleteBoard(1L);

        ResponseEntity<CommonResponse<String>> response = boardController.deleteBoard(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Deletion failed"));
    }

    @Test
    void testGetBoardsByTeamAndHackathon_Success() {
        when(boardService.getBoardsByTeamAndHackathon(1L, 2L))
                .thenReturn(Collections.singletonList(new BoardResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response =
                boardController.getBoardsByTeamAndHackathon(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardsByTeamAndHackathon_Exception() {
        when(boardService.getBoardsByTeamAndHackathon(1L, 2L)).thenThrow(new RuntimeException("Error getting boards"));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response =
                boardController.getBoardsByTeamAndHackathon(1L, 2L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Error getting boards"));
    }

    @Test
    void testGetAdminBoards_Success() {
        when(boardService.getAdminBoards()).thenReturn(List.of(new BoardResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response = boardController.getAdminBoards();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAdminBoards_Exception() {
        when(boardService.getAdminBoards()).thenThrow(new RuntimeException("Admin fetch failed"));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response = boardController.getAdminBoards();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Admin fetch failed"));
    }

    @Test
    void testGetHackathonOperatingBoards_Success() {
        when(boardService.getHackathonOperatingBoards(1L)).thenReturn(List.of(new BoardResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response =
                boardController.getHackathonOperatingBoards(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetHackathonOperatingBoards_Exception() {
        when(boardService.getHackathonOperatingBoards(1L)).thenThrow(new RuntimeException("Hackathon error"));

        ResponseEntity<CommonResponse<List<BoardResponseDTO>>> response =
                boardController.getHackathonOperatingBoards(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Hackathon error"));
    }

}
