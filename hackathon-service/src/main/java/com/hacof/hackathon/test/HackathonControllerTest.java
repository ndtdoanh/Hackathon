package com.hacof.hackathon.test;

import com.hacof.hackathon.controller.HackathonController;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


class HackathonControllerTest {

    @Mock
    private HackathonService hackathonService;

    @InjectMocks
    private HackathonController hackathonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByAllCriteria() {
        HackathonDTO hackathonDTO = new HackathonDTO();
        hackathonDTO.setId("1");
        hackathonDTO.setTitle("Test Hackathon");

        when(hackathonService.getHackathons(any())).thenReturn(Collections.singletonList(hackathonDTO));

        ResponseEntity<CommonResponse<List<HackathonDTO>>> response = hackathonController.getByAllCriteria(
                "1", "Test Hackathon", null, null, null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Test Hackathon", response.getBody().getData().get(0).getTitle());
    }

    @Test
    void testCreateHackathon() {
        HackathonDTO hackathonDTO = new HackathonDTO();
        hackathonDTO.setId("1");
        hackathonDTO.setTitle("New Hackathon");

        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(hackathonDTO);

        when(hackathonService.create(hackathonDTO)).thenReturn(hackathonDTO);

        ResponseEntity<CommonResponse<HackathonDTO>> response = hackathonController.createHackathon(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Hackathon", response.getBody().getData().getTitle());
    }

    @Test
    void testUpdateHackathon() {
        HackathonDTO hackathonDTO = new HackathonDTO();
        hackathonDTO.setId("1");
        hackathonDTO.setTitle("Updated Hackathon");

        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(hackathonDTO);

        when(hackathonService.update("1", hackathonDTO)).thenReturn(hackathonDTO);

        ResponseEntity<CommonResponse<HackathonDTO>> response = hackathonController.updateHackathon(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Hackathon", response.getBody().getData().getTitle());
    }

    @Test
    void testDeleteHackathon() {
        doNothing().when(hackathonService).deleteHackathon(1L);

        ResponseEntity<CommonResponse<Void>> response = hackathonController.deleteHackathon("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(hackathonService, times(1)).deleteHackathon(1L);
    }
}