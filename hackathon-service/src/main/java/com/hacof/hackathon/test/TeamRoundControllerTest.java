package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hacof.hackathon.controller.TeamRoundController;
import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.service.TeamRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class TeamRoundControllerTest {

    @Mock
    private TeamRoundService teamRoundService;

    @InjectMocks
    private TeamRoundController teamRoundController;

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
    void testCreateTeamRound() {
        TeamRoundDTO dto = new TeamRoundDTO();
        dto.setId("1");
        dto.setTeamId("Test Team Round");

        CommonRequest<TeamRoundDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(teamRoundService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<TeamRoundDTO>> response = teamRoundController.createTeamRound(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertNotNull(response.getBody().getData(), "Response body data should not be null");
        assertEquals("Test Team Round", response.getBody().getData().getTeamId());
        verify(teamRoundService, times(1)).create(dto);
    }

    @Test
    void testGetAllByRoundId() {
        TeamRoundDTO dto = new TeamRoundDTO();
        dto.setId("1");

        when(teamRoundService.getAllByRoundId("round1")).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<TeamRoundDTO>>> response = teamRoundController.getAllByRoundId("round1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body should not be null");
        assertNotNull(response.getBody().getData(), "Response body data should not be null");
        assertEquals("1", response.getBody().getData().get(0).getId());
        verify(teamRoundService, times(1)).getAllByRoundId("round1");
    }

    @Test
    void testDeleteTeamRound() {
        doNothing().when(teamRoundService).delete("1");

        ResponseEntity<CommonResponse<Void>> response = teamRoundController.deleteTeamRound("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teamRoundService, times(1)).delete("1");
    }

    @Test
    void testCreateTeamRoundWithNullInput() {
        CommonRequest<TeamRoundDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(null);

        ResponseEntity<CommonResponse<TeamRoundDTO>> response = teamRoundController.createTeamRound(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(teamRoundService, never()).create(any(TeamRoundDTO.class));
    }
}
