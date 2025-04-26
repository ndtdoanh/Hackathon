package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserHackathonBulkRequestDTO;
import com.hacof.identity.dto.request.UserHackathonRequestDTO;
import com.hacof.identity.dto.response.UserHackathonResponseDTO;
import com.hacof.identity.service.UserHackathonService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserHackathonControllerTest {

    @InjectMocks
    UserHackathonController userHackathonController;

    @Mock
    UserHackathonService userHackathonService;

    @Test
    void testCreateUserHackathon() {
        ApiRequest<UserHackathonRequestDTO> request = new ApiRequest<>();
        UserHackathonRequestDTO requestDTO = new UserHackathonRequestDTO();
        request.setData(requestDTO);
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");

        UserHackathonResponseDTO mockResponse = new UserHackathonResponseDTO();
        when(userHackathonService.createUserHackathon(requestDTO)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<UserHackathonResponseDTO>> response =
                userHackathonController.createUserHackathon(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody().getData());
        verify(userHackathonService, times(1)).createUserHackathon(requestDTO);
    }

    @Test
    void testGetUserHackathons() {
        List<UserHackathonResponseDTO> mockList =
                List.of(new UserHackathonResponseDTO(), new UserHackathonResponseDTO());
        when(userHackathonService.getUserHackathons()).thenReturn(mockList);

        ApiResponse<List<UserHackathonResponseDTO>> response = userHackathonController.getUserHackathons();

        assertEquals(mockList, response.getData());
        verify(userHackathonService, times(1)).getUserHackathons();
    }

    @Test
    void testGetUserHackathonById() {
        Long id = 1L;
        UserHackathonResponseDTO mockResponse = new UserHackathonResponseDTO();
        when(userHackathonService.getUserHackathon(id)).thenReturn(mockResponse);

        ApiResponse<UserHackathonResponseDTO> response = userHackathonController.getUserHackathon(id);

        assertEquals(mockResponse, response.getData());
        verify(userHackathonService, times(1)).getUserHackathon(id);
    }

    @Test
    void testGetUserHackathonsByHackathonId() {
        Long hackathonId = 1L;
        List<UserHackathonResponseDTO> mockList = List.of(new UserHackathonResponseDTO());
        when(userHackathonService.getUserHackathonsByHackathonId(hackathonId)).thenReturn(mockList);

        ApiResponse<List<UserHackathonResponseDTO>> response =
                userHackathonController.getUserHackathonsByHackathonId(hackathonId);

        assertEquals(mockList, response.getData());
        verify(userHackathonService, times(1)).getUserHackathonsByHackathonId(hackathonId);
    }

    @Test
    void testGetUserHackathonsByHackathonIdAndRoles() {
        Long hackathonId = 1L;
        List<String> roles = List.of("MENTOR", "JUDGE");
        List<UserHackathonResponseDTO> mockList = List.of(new UserHackathonResponseDTO());
        when(userHackathonService.getUserHackathonsByHackathonIdAndRoles(hackathonId, roles))
                .thenReturn(mockList);

        ApiResponse<List<UserHackathonResponseDTO>> response =
                userHackathonController.getUserHackathonsByHackathonIdAndRoles(hackathonId, roles);

        assertEquals(mockList, response.getData());
        verify(userHackathonService, times(1)).getUserHackathonsByHackathonIdAndRoles(hackathonId, roles);
    }

    @Test
    void testDeleteUserHackathon() {
        Long id = 1L;

        ApiResponse<Void> response = userHackathonController.deleteUserHackathon(id);

        assertEquals("UserHackathon has been deleted", response.getMessage());
        verify(userHackathonService, times(1)).deleteUserHackathon(id);
    }

    @Test
    void testCreateBulkUserHackathon() {
        ApiRequest<UserHackathonBulkRequestDTO> request = new ApiRequest<>();
        UserHackathonBulkRequestDTO bulkRequestDTO = new UserHackathonBulkRequestDTO();
        request.setData(bulkRequestDTO);
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");

        List<UserHackathonResponseDTO> mockList =
                List.of(new UserHackathonResponseDTO(), new UserHackathonResponseDTO());
        when(userHackathonService.createBulkUserHackathon(bulkRequestDTO)).thenReturn(mockList);

        ResponseEntity<ApiResponse<List<UserHackathonResponseDTO>>> response =
                userHackathonController.createBulkUserHackathon(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockList, response.getBody().getData());
        verify(userHackathonService, times(1)).createBulkUserHackathon(bulkRequestDTO);
    }
}
