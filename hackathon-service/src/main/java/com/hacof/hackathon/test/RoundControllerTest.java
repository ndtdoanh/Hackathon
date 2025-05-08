package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
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

import com.hacof.hackathon.controller.RoundController;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class RoundControllerTest {

    @Mock
    private RoundService roundService;

    @InjectMocks
    private RoundController roundController;

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
    void testCreateRound() {
        RoundDTO dto = new RoundDTO();
        dto.setId("1");

        CommonRequest<RoundDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(roundService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<RoundDTO>> response = roundController.createRound(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(roundService, times(1)).create(dto);
    }

    @Test
    void testGetRounds() {
        RoundDTO dto = new RoundDTO();
        dto.setId("1");

        when(roundService.getRounds(any())).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<RoundDTO>>> response =
                roundController.getRounds(null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(roundService, times(1)).getRounds(any());
    }
}
