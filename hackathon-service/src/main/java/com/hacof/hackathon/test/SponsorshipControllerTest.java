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

import com.hacof.hackathon.controller.SponsorshipController;
import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.service.SponsorshipService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class SponsorshipControllerTest {

    @Mock
    private SponsorshipService sponsorshipService;

    @InjectMocks
    private SponsorshipController sponsorshipController;

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
    void testCreateSponsorship() {
        SponsorshipDTO dto = new SponsorshipDTO();
        dto.setId("1");

        CommonRequest<SponsorshipDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(sponsorshipService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<SponsorshipDTO>> response = sponsorshipController.createSponsorship(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(sponsorshipService, times(1)).create(dto);
    }

    @Test
    void testGetSponsorships() {
        SponsorshipDTO dto = new SponsorshipDTO();
        dto.setId("1");

        when(sponsorshipService.getAll(any())).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<SponsorshipDTO>>> response =
                sponsorshipController.getSponsorships(null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(sponsorshipService, times(1)).getAll(any());
    }
}
