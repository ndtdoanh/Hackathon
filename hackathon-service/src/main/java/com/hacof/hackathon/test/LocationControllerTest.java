package com.hacof.hackathon.test;

import com.hacof.hackathon.controller.LocationController;
import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.service.LocationService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLocations() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId("1");
        locationDTO.setName("Test Location");

        when(locationService.getLocations(any(Specification.class)))
                .thenReturn(Collections.singletonList(locationDTO));

        ResponseEntity<CommonResponse<List<LocationDTO>>> response = locationController.getLocations(
                "1", "Test Location", null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Test Location", response.getBody().getData().get(0).getName());
        verify(locationService, times(1)).getLocations(any(Specification.class));
    }

    @Test
    void testCreateLocation() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId("1");
        locationDTO.setName("New Location");

        CommonRequest<LocationDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(locationDTO);

        when(locationService.create(locationDTO)).thenReturn(locationDTO);

        ResponseEntity<CommonResponse<LocationDTO>> response = locationController.createLocation(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Location", response.getBody().getData().getName());
        verify(locationService, times(1)).create(locationDTO);
    }

    @Test
    void testUpdateLocation() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId("1");
        locationDTO.setName("Updated Location");

        CommonRequest<LocationDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(locationDTO);

        when(locationService.update(1L, locationDTO)).thenReturn(locationDTO);

        ResponseEntity<CommonResponse<LocationDTO>> response = locationController.updateLocation(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Location", response.getBody().getData().getName());
        verify(locationService, times(1)).update(1L, locationDTO);
    }

    @Test
    void testDeleteLocation() {
        doNothing().when(locationService).delete(1L);

        ResponseEntity<CommonResponse<LocationDTO>> response = locationController.deleteLocation("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(locationService, times(1)).delete(1L);
    }
}