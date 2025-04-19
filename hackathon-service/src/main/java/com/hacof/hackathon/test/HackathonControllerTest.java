package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hacof.hackathon.controller.HackathonController;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.service.HackathonService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class HackathonControllerTest {

    @Mock
    private HackathonService hackathonService;

    @InjectMocks
    private HackathonController hackathonController;
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
    void testGetByAllCriteria() {
        HackathonDTO hackathonDTO = new HackathonDTO();
        hackathonDTO.setId("1");
        hackathonDTO.setTitle("Test Hackathon");

        when(hackathonService.getHackathons(any())).thenReturn(Collections.singletonList(hackathonDTO));

        ResponseEntity<CommonResponse<List<HackathonDTO>>> response =
                hackathonController.getByAllCriteria("1", "Test Hackathon", null, null, null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<HackathonDTO> data = response.getBody().getData();
        assertNotNull(data);
        assertEquals(1, data.size());

        HackathonDTO first = data.stream().findFirst().orElse(null);
        assertNotNull(first);
        assertEquals("Test Hackathon", first.getTitle());
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

    @Test
    void testCreateHackathonWithNullInput() {
        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(null);

        ResponseEntity<CommonResponse<HackathonDTO>> response =
                hackathonController.createHackathon(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(hackathonService, never()).create(any(HackathonDTO.class));
    }

    @Test
    void testUpdateHackathonWithInvalidId() {
        HackathonDTO dto = new HackathonDTO();
        dto.setId("999");
        dto.setTitle("Updated Hackathon");

        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(hackathonService.update("999", dto))
                .thenThrow(new ResourceNotFoundException("Hackathon not found"));

        ResponseEntity<CommonResponse<HackathonDTO>> response =
                hackathonController.updateHackathon(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(hackathonService, times(1)).update("999", dto);
    }

    @Test
    void testCreateHackathonWithDuplicateTitle() {
        HackathonDTO dto = new HackathonDTO();
        dto.setTitle("Duplicate Title");

        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(hackathonService.create(dto))
                .thenThrow(new InvalidInputException("Hackathon with title 'Duplicate Title' already exists."));

        ResponseEntity<CommonResponse<HackathonDTO>> response =
                hackathonController.createHackathon(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(hackathonService, times(1)).create(dto);
    }

    @Test
    void testUpdateHackathonWithEmptyTitle() {
        HackathonDTO dto = new HackathonDTO();
        dto.setId("1");
        dto.setTitle("");

        CommonRequest<HackathonDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(hackathonService.update("1", dto))
                .thenThrow(new InvalidInputException("Title cannot be empty"));

        ResponseEntity<CommonResponse<HackathonDTO>> response =
                hackathonController.updateHackathon(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(hackathonService, times(1)).update("1", dto);
    }


    @Test
    void testDeleteNonExistentHackathon() {
        doThrow(new ResourceNotFoundException("Hackathon not found"))
                .when(hackathonService).deleteHackathon(1L);

        ResponseEntity<CommonResponse<Void>> response = hackathonController.deleteHackathon("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(hackathonService, times(1)).deleteHackathon(1L);
    }
}
