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

import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;
import com.hacof.communication.service.BoardUserService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class BoardUserControllerTest {

    @Mock
    private BoardUserService boardUserService;

    @InjectMocks
    private BoardUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<BoardUserRequestDTO> buildRequest() {
        CommonRequest<BoardUserRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new BoardUserRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_AllFieldsNull() {
        CommonRequest<BoardUserRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new BoardUserRequestDTO());

        when(boardUserService.createBoardUser(any())).thenReturn(new BoardUserResponseDTO());

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.createBoardUser(request);
        CommonResponse<BoardUserResponseDTO> body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateBoardUser_Success() {
        when(boardUserService.createBoardUser(any())).thenReturn(new BoardUserResponseDTO());

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.createBoardUser(buildRequest());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateBoardUser_IllegalArgument() {
        when(boardUserService.createBoardUser(any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.createBoardUser(buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateBoardUser_Exception() {
        when(boardUserService.createBoardUser(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.createBoardUser(buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Error"));
    }

    @Test
    void testUpdateBoardUser_Success() {
        when(boardUserService.updateBoardUser(eq(1L), any())).thenReturn(new BoardUserResponseDTO());

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.updateBoardUser(1L, buildRequest());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBoardUser_IllegalArgument() {
        when(boardUserService.updateBoardUser(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.updateBoardUser(1L, buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateBoardUser_Exception() {
        when(boardUserService.updateBoardUser(eq(1L), any())).thenThrow(new RuntimeException("Unexpected"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.updateBoardUser(1L, buildRequest());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteBoardUser_Success() {
        when(boardUserService.deleteBoardUser(1L)).thenReturn(new BoardUserResponseDTO());

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.deleteBoardUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("BoardUser deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardUser_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid ID"))
                .when(boardUserService)
                .deleteBoardUser(1L);

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.deleteBoardUser(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid ID", response.getBody().getMessage());
    }

    @Test
    void testDeleteBoardUser_Exception() {
        doThrow(new RuntimeException("Failed")).when(boardUserService).deleteBoardUser(1L);

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.deleteBoardUser(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Failed"));
    }

    @Test
    void testGetBoardUser_Success() {
        when(boardUserService.getBoardUser(1L)).thenReturn(new BoardUserResponseDTO());

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.getBoardUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetBoardUser_IllegalArgument() {
        when(boardUserService.getBoardUser(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.getBoardUser(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardUser_Exception() {
        when(boardUserService.getBoardUser(1L)).thenThrow(new RuntimeException("Crash"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.getBoardUser(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllBoardUsers_Success() {
        when(boardUserService.getAllBoardUsers()).thenReturn(List.of(new BoardUserResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getAllBoardUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllBoardUsers_Exception() {
        when(boardUserService.getAllBoardUsers()).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getAllBoardUsers();

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardUsersByBoardId_Success() {
        when(boardUserService.getBoardUsersByBoardId(10L)).thenReturn(List.of(new BoardUserResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getBoardUsersByBoardId(10L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetBoardUsersByBoardId_Exception() {
        when(boardUserService.getBoardUsersByBoardId(10L)).thenThrow(new RuntimeException("Board failed"));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getBoardUsersByBoardId(10L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetBoardUsersByUserId_Success() {
        when(boardUserService.getBoardUsersByUserId(22L))
                .thenReturn(Collections.singletonList(new BoardUserResponseDTO()));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getBoardUsersByUserId(22L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetBoardUsersByUserId_Exception() {
        when(boardUserService.getBoardUsersByUserId(22L)).thenThrow(new RuntimeException("User error"));

        ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> response = controller.getBoardUsersByUserId(22L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUndeleteBoardUser_Success() {
        BoardUserResponseDTO mockDTO = new BoardUserResponseDTO();
        when(boardUserService.undeleteBoardUser(1L)).thenReturn(mockDTO);

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.undeleteBoardUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("BoardUser undeleted successfully!", response.getBody().getMessage());
        assertEquals(mockDTO, response.getBody().getData());
    }

    @Test
    void testUndeleteBoardUser_IllegalArgument() {
        when(boardUserService.undeleteBoardUser(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.undeleteBoardUser(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUndeleteBoardUser_Exception() {
        when(boardUserService.undeleteBoardUser(1L)).thenThrow(new RuntimeException("Unexpected crash"));

        ResponseEntity<CommonResponse<BoardUserResponseDTO>> response = controller.undeleteBoardUser(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected crash", response.getBody().getMessage());
    }
}
