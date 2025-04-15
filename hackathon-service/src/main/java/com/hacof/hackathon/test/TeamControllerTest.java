package com.hacof.hackathon.test;

import com.hacof.hackathon.controller.TeamController;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBulkTeams() {
        TeamDTO dto = new TeamDTO();
        dto.setId("1");

        when(teamService.createBulkTeams(any(), any())).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<TeamDTO>>> response = teamController.createBulkTeams(Collections.singletonMap("teamLeaderId", "1"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(teamService, times(1)).createBulkTeams(any(), any());
    }

    @Test
    void testGetAllTeams() {
        TeamDTO dto = new TeamDTO();
        dto.setId("1");

        when(teamService.getAllTeams()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<TeamDTO>>> response = teamController.getAllTeams();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(teamService, times(1)).getAllTeams();
    }
}