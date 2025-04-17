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

import com.hacof.hackathon.controller.MentorTeamLimitController;
import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.service.MentorTeamLimitService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class MentorTeamLimitControllerTest {

    @Mock
    private MentorTeamLimitService mentorTeamLimitService;

    @InjectMocks
    private MentorTeamLimitController mentorTeamLimitController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
                mentorTeamLimitController.createMentorTeamLimit(request);

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
                mentorTeamLimitController.updateMentorTeamLimit(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamLimitService, times(1)).update(Long.parseLong(dto.getId()), dto);
    }

    @Test
    void testDeleteMentorTeamLimit() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        CommonRequest<MentorTeamLimitDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        doNothing().when(mentorTeamLimitService).delete(Long.parseLong(dto.getId()));

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response =
                mentorTeamLimitController.deleteMentorTeamLimit(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mentorTeamLimitService, times(1)).delete(Long.parseLong(dto.getId()));
    }

    @Test
    void testGetAllMentorTeamLimits() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        when(mentorTeamLimitService.getAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<MentorTeamLimitDTO>>> response =
                mentorTeamLimitController.getAllMentorTeamLimits();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(mentorTeamLimitService, times(1)).getAll();
    }

    @Test
    void testGetMentorTeamLimitById() {
        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId("1");

        when(mentorTeamLimitService.getById(1L)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorTeamLimitDTO>> response =
                mentorTeamLimitController.getMentorTeamLimitById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorTeamLimitService, times(1)).getById(1L);
    }
}
