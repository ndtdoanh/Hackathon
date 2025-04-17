package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeamRound() {
        TeamRoundDTO dto = new TeamRoundDTO();
        dto.setId("1");

        CommonRequest<TeamRoundDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(teamRoundService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<TeamRoundDTO>> response = teamRoundController.createTeamRound(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(teamRoundService, times(1)).create(dto);
    }

    @Test
    void testGetAllByRoundId() {
        TeamRoundDTO dto = new TeamRoundDTO();
        dto.setId("1");

        when(teamRoundService.getAllByRoundId("round1")).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<TeamRoundDTO>>> response = teamRoundController.getAllByRoundId("round1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(teamRoundService, times(1)).getAllByRoundId("round1");
    }

    @Test
    void testDeleteTeamRound() {
        doNothing().when(teamRoundService).delete("1");

        ResponseEntity<CommonResponse<Void>> response = teamRoundController.deleteTeamRound("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teamRoundService, times(1)).delete("1");
    }
}
