package com.hacof.hackathon.test;

import com.hacof.hackathon.controller.IndividualRegistrationRequestController;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
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
import static org.mockito.Mockito.*;

class IndividualRegistrationRequestControllerTest {

    @Mock
    private IndividualRegistrationRequestService individualRegistrationRequestService;

    @InjectMocks
    private IndividualRegistrationRequestController individualRegistrationRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIndividualRegistration() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");
        dto.setCreatedByUserName("user1");

        CommonRequest<IndividualRegistrationRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(individualRegistrationRequestService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> response =
                individualRegistrationRequestController.createIndividualRegistration(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(individualRegistrationRequestService, times(1)).create(dto);
    }

    @Test
    void testUpdateIndividualRegistration() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");
        dto.setCreatedByUserName("user1");

        CommonRequest<IndividualRegistrationRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(individualRegistrationRequestService.update(1L, dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> response =
                individualRegistrationRequestController.updateIndividualRegistration(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(individualRegistrationRequestService, times(1)).update(1L, dto);
    }

    @Test
    void testDeleteIndividualRegistration() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");

        doNothing().when(individualRegistrationRequestService).delete(1L);

        ResponseEntity<CommonResponse<Void>> response =
                individualRegistrationRequestController.deleteIndividualRegistration(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(individualRegistrationRequestService, times(1)).delete(1L);
    }

    @Test
    void testGetAllIndividualRegistrations() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");

        when(individualRegistrationRequestService.getAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> response =
                individualRegistrationRequestController.getAllIndividualRegistrations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(individualRegistrationRequestService, times(1)).getAll();
    }

    @Test
    void testGetIndividualRegistrationById() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");

        when(individualRegistrationRequestService.getById(1L)).thenReturn(dto);

        ResponseEntity<CommonResponse<IndividualRegistrationRequestDTO>> response =
                individualRegistrationRequestController.getIndividualRegistrationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(individualRegistrationRequestService, times(1)).getById(1L);
    }

    @Test
    void testGetAllByCreatedByUsername() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setCreatedByUserName("user1");

        when(individualRegistrationRequestService.getAllByCreatedByUsername("user1"))
                .thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> response =
                individualRegistrationRequestController.getAllByCreatedByUsername("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(individualRegistrationRequestService, times(1)).getAllByCreatedByUsername("user1");
    }
}