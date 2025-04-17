package com.hacof.submission;

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

import com.hacof.submission.controller.TeamRoundJudgeController;
import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.service.TeamRoundJudgeService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;

class TeamRoundJudgeControllerTest {

    @Mock
    private TeamRoundJudgeService service;

    @InjectMocks
    private TeamRoundJudgeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<TeamRoundJudgeRequestDTO> buildRequestWithData() {
        CommonRequest<TeamRoundJudgeRequestDTO> request = new CommonRequest<>();
        request.setData(new TeamRoundJudgeRequestDTO());
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        return request;
    }

    @Test
    void testSetCommonResponseFields_AllFieldsNull() {
        CommonRequest<TeamRoundJudgeRequestDTO> request = new CommonRequest<>();
        request.setData(new TeamRoundJudgeRequestDTO()); // Không set requestId, dateTime, channel

        TeamRoundJudgeResponseDTO mockResponse = new TeamRoundJudgeResponseDTO();
        when(service.createTeamRoundJudge(any())).thenReturn(mockResponse);

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.create(request);

        CommonResponse<TeamRoundJudgeResponseDTO> resBody = response.getBody();
        assertNotNull(resBody);
        assertNotNull(resBody.getRequestId()); // UUID được gen
        assertNotNull(resBody.getRequestDateTime()); // LocalDateTime.now()
        assertEquals("HACOF", resBody.getChannel()); // default fallback
    }

    @Test
    void testSetCommonResponseFields_AllFieldsPresent() {
        CommonRequest<TeamRoundJudgeRequestDTO> request = new CommonRequest<>();
        request.setData(new TeamRoundJudgeRequestDTO());
        request.setRequestId("fixed-request-id");
        request.setRequestDateTime(LocalDateTime.of(2024, 1, 1, 10, 0));
        request.setChannel("CUSTOM");

        TeamRoundJudgeResponseDTO mockResponse = new TeamRoundJudgeResponseDTO();
        when(service.createTeamRoundJudge(any())).thenReturn(mockResponse);

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.create(request);

        CommonResponse<TeamRoundJudgeResponseDTO> resBody = response.getBody();
        assertEquals("fixed-request-id", resBody.getRequestId());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), resBody.getRequestDateTime());
        assertEquals("CUSTOM", resBody.getChannel());
    }

    @Test
    void testGetAll_Success() {
        when(service.getAllTeamRoundJudges()).thenReturn(List.of(new TeamRoundJudgeResponseDTO()));

        ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> response = controller.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAll_Exception() {
        when(service.getAllTeamRoundJudges()).thenThrow(new RuntimeException("Service error"));

        ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> response = controller.getAll();

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Success() {
        when(service.getTeamRoundJudgeById(1L)).thenReturn(new TeamRoundJudgeResponseDTO());

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_IllegalArgument() {
        when(service.getTeamRoundJudgeById(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Exception() {
        when(service.getTeamRoundJudgeById(1L)).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.getById(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        when(service.createTeamRoundJudge(any())).thenReturn(new TeamRoundJudgeResponseDTO());

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.create(buildRequestWithData());

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreate_IllegalArgument() {
        when(service.createTeamRoundJudge(any())).thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.create(buildRequestWithData());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        when(service.createTeamRoundJudge(any())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response = controller.create(buildRequestWithData());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        when(service.updateTeamRoundJudge(eq(1L), any())).thenReturn(new TeamRoundJudgeResponseDTO());

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_IllegalArgument() {
        when(service.updateTeamRoundJudge(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        when(service.updateTeamRoundJudge(eq(1L), any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> response =
                controller.update(1L, buildRequestWithData());

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(service).deleteTeamRoundJudge(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDelete_IllegalArgument() {
        doThrow(new IllegalArgumentException("Not found")).when(service).deleteTeamRoundJudge(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        doThrow(new RuntimeException("Failed")).when(service).deleteTeamRoundJudge(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.delete(1L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByTeamRoundId_Success() {
        when(service.getTeamRoundJudgesByTeamRoundId(99L)).thenReturn(List.of(new TeamRoundJudgeResponseDTO()));

        ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> response = controller.getByTeamRoundId(99L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByTeamRoundId_IllegalArgument() {
        when(service.getTeamRoundJudgesByTeamRoundId(99L)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> response = controller.getByTeamRoundId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByTeamRoundId_Exception() {
        when(service.getTeamRoundJudgesByTeamRoundId(99L)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> response = controller.getByTeamRoundId(99L);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteByTeamRoundAndJudge_Success() {
        doNothing().when(service).deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteByTeamRoundAndJudge(10L, 20L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteByTeamRoundAndJudge_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid"))
                .when(service)
                .deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteByTeamRoundAndJudge(10L, 20L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteByTeamRoundAndJudge_Exception() {
        doThrow(new RuntimeException("Internal")).when(service).deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(10L, 20L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteByTeamRoundAndJudge(10L, 20L);

        assertEquals(500, response.getStatusCodeValue());
    }
}
