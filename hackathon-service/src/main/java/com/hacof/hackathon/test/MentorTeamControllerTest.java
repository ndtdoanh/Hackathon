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

import com.hacof.hackathon.controller.MentorTeamController;
import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.service.MentorTeamLimitService;
import com.hacof.hackathon.service.MentorTeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class MentorTeamControllerTest {

    @Mock
    private MentorTeamService mentorTeamService;

    @Mock
    private MentorTeamLimitService mentorTeamLimitService;

    @InjectMocks
    private MentorTeamController mentorTeamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMentorTeam() {
        MentorTeamDTO dto = new MentorTeamDTO();
        dto.setId("1");

        CommonRequest<MentorTeamDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorTeamService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamDTO>> response = mentorTeamController.createMentorTeam(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamService, times(1)).create(dto);
    }

    @Test
    void testUpdateMentorTeam() {
        MentorTeamDTO dto = new MentorTeamDTO();
        dto.setId("1");

        CommonRequest<MentorTeamDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorTeamService.update(dto.getId(), dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamDTO>> response = mentorTeamController.updateMentorTeam(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamService, times(1)).update(dto.getId(), dto);
    }

    @Test
    void testDeleteMentorTeam() {
        doNothing().when(mentorTeamService).delete("1");

        ResponseEntity<CommonResponse<Void>> response = mentorTeamController.deleteMentorTeam("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mentorTeamService, times(1)).delete("1");
    }

    @Test
    void testGetAllByHackathonIdAndTeamId() {
        MentorTeamDTO dto = new MentorTeamDTO();
        dto.setId("1");

        when(mentorTeamService.getAllByHackathonIdAndTeamId("hackathon1", "team1"))
                .thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<MentorTeamDTO>>> response =
                mentorTeamController.getAllByHackathonIdAndTeamId("hackathon1", "team1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(mentorTeamService, times(1)).getAllByHackathonIdAndTeamId("hackathon1", "team1");
    }

    @Test
    void testGetAllByMentorId() {
        MentorTeamDTO dto = new MentorTeamDTO();
        dto.setId("1");

        when(mentorTeamService.getAllByMentorId("mentor1")).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<MentorTeamDTO>>> response = mentorTeamController.getAllByMentorId("mentor1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(mentorTeamService, times(1)).getAllByMentorId("mentor1");
    }

    @Test
    void testCreateMentorTeamLimit() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        CommonRequest<MentorTeamLimitDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorTeamLimitService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response =
                mentorTeamController.createMentorTeamLimit(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamLimitService, times(1)).create(dto);
    }

    @Test
    void testUpdateMentorTeamLimit() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        CommonRequest<MentorTeamLimitDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorTeamLimitService.update(Long.parseLong(dto.getId()), dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response =
                mentorTeamController.updateMentorTeamLimit(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamLimitService, times(1)).update(Long.parseLong(dto.getId()), dto);
    }

    @Test
    void testDeleteMentorTeamLimit() {
        doNothing().when(mentorTeamLimitService).delete(1L);

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response = mentorTeamController.deleteMentorTeamLimit("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mentorTeamLimitService, times(1)).delete(1L);
    }

    @Test
    void testGetAllMentorTeamLimits() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        when(mentorTeamLimitService.getAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<MentorTeamLimitDTO>>> response =
                mentorTeamController.getAllMentorTeamLimits();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(mentorTeamLimitService, times(1)).getAll();
    }

    @Test
    void testGetMentorTeamLimitById() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        when(mentorTeamLimitService.getById(1L)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response = mentorTeamController.getMentorTeamLimitById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamLimitService, times(1)).getById(1L);
    }
}
