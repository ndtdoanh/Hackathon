package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.hacof.hackathon.controller.IndividualRegistrationRequestController;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class IndividualRegistrationRequestControllerTest {

    @Mock
    private IndividualRegistrationRequestService individualRegistrationRequestService;

    @InjectMocks
    private IndividualRegistrationRequestController individualRegistrationRequestController;

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
    void testCreateIndividualRegistration() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("1");
        dto.setHackathonId("123"); // Provide a valid Hackathon ID
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
        assertEquals(
                "1",
                response.getBody() != null && response.getBody().getData() != null
                        ? response.getBody().getData().getId()
                        : null);
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
        assertEquals(
                "1",
                response.getBody() != null && response.getBody().getData() != null
                        ? response.getBody().getData().getId()
                        : null);
        verify(individualRegistrationRequestService, times(1)).update(1L, dto);
    }

    void testDeleteIndividualRegistration() {
        Long id = 1L;

        doNothing().when(individualRegistrationRequestService).delete(id);

        ResponseEntity<CommonResponse<Void>> response =
                individualRegistrationRequestController.deleteIndividualRegistration(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(individualRegistrationRequestService, times(1)).delete(id);
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

    @Test
    void testCreateIndividualRegistrationWithNullHackathonId() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setHackathonId(null); // Hackathon ID is null

        CommonRequest<IndividualRegistrationRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        InvalidInputException exception = assertThrows(
                InvalidInputException.class,
                () -> individualRegistrationRequestController.createIndividualRegistration(request));

        assertEquals("Hackathon ID cannot be null", exception.getMessage());
        verify(individualRegistrationRequestService, never()).create(any(IndividualRegistrationRequestDTO.class));
    }

    @Test
    void testCreateIndividualRegistrationWithInvalidHackathonId() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setHackathonId("999");

        CommonRequest<IndividualRegistrationRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(individualRegistrationRequestService.create(dto))
                .thenThrow(new ResourceNotFoundException("Hackathon not found"));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> individualRegistrationRequestController.createIndividualRegistration(request));

        assertEquals("Hackathon not found", exception.getMessage());
        verify(individualRegistrationRequestService, times(1)).create(dto);
    }

    @Test
    void testUpdateNonExistentIndividualRegistration() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId("999");

        CommonRequest<IndividualRegistrationRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(individualRegistrationRequestService.update(999L, dto))
                .thenThrow(new ResourceNotFoundException("Individual registration request not found"));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> individualRegistrationRequestController.updateIndividualRegistration(request));

        assertEquals("Individual registration request not found", exception.getMessage());
        verify(individualRegistrationRequestService, times(1)).update(999L, dto);
    }

    @Test
    void testDeleteNonExistentIndividualRegistration() {
        Long id = 999L;

        doThrow(new ResourceNotFoundException("Individual registration request not found"))
                .when(individualRegistrationRequestService)
                .delete(id);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> individualRegistrationRequestController.deleteIndividualRegistration(id));

        assertEquals("Individual registration request not found", exception.getMessage());
        verify(individualRegistrationRequestService, times(1)).delete(id);
    }

    @Test
    void testGetAllByHackathonId() {
        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setHackathonId("1");

        when(individualRegistrationRequestService.getAllByHackathonId("1")).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<IndividualRegistrationRequestDTO>>> response =
                individualRegistrationRequestController.getAllByHackathonId("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(individualRegistrationRequestService, times(1)).getAllByHackathonId("1");
    }
}
