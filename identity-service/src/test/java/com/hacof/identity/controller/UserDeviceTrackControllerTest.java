package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.service.UserDeviceTrackService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
class UserDeviceTrackControllerTest {

    @InjectMocks
    UserDeviceTrackController userDeviceTrackController;

    @Mock
    UserDeviceTrackService userDeviceTrackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserDeviceTrack() throws Exception {
        UserDeviceTrackRequest request = new UserDeviceTrackRequest();
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()));
        UserDeviceTrackResponse mockResponse = new UserDeviceTrackResponse();

        when(userDeviceTrackService.createUserDeviceTrack(request, files)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<UserDeviceTrackResponse>> response =
                userDeviceTrackController.createUserDeviceTrack(request, files);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody().getData());
        verify(userDeviceTrackService, times(1)).createUserDeviceTrack(request, files);
    }

    @Test
    void testGetUserDeviceTracks() {
        List<UserDeviceTrackResponse> mockList = List.of(new UserDeviceTrackResponse(), new UserDeviceTrackResponse());
        when(userDeviceTrackService.getUserDeviceTracks()).thenReturn(mockList);

        ApiResponse<List<UserDeviceTrackResponse>> response = userDeviceTrackController.getUserDeviceTracks();

        assertEquals(mockList, response.getData());
        verify(userDeviceTrackService, times(1)).getUserDeviceTracks();
    }

    @Test
    void testGetUserDeviceTrackById() {
        Long id = 1L;
        UserDeviceTrackResponse mockResponse = new UserDeviceTrackResponse();
        when(userDeviceTrackService.getUserDeviceTrack(id)).thenReturn(mockResponse);

        ApiResponse<UserDeviceTrackResponse> response = userDeviceTrackController.getUserDeviceTrackById(id);

        assertEquals(mockResponse, response.getData());
        verify(userDeviceTrackService, times(1)).getUserDeviceTrack(id);
    }

    @Test
    void testGetUserDeviceTracksByUserDeviceId() {
        Long userDeviceId = 1L;
        List<UserDeviceTrackResponse> mockList = List.of(new UserDeviceTrackResponse());
        when(userDeviceTrackService.getUserDeviceTracksByUserDeviceId(userDeviceId))
                .thenReturn(mockList);

        ApiResponse<List<UserDeviceTrackResponse>> response =
                userDeviceTrackController.getUserDeviceTracksByUserDeviceId(userDeviceId);

        assertEquals(mockList, response.getData());
        verify(userDeviceTrackService, times(1)).getUserDeviceTracksByUserDeviceId(userDeviceId);
    }

    @Test
    void testGetFileUrlsByUserDeviceTrackId() {
        Long userDeviceTrackId = 1L;
        List<FileUrlResponse> mockFiles = List.of(new FileUrlResponse());
        when(userDeviceTrackService.getFileUrlsByUserDeviceTrackId(userDeviceTrackId))
                .thenReturn(mockFiles);

        ApiResponse<List<FileUrlResponse>> response =
                userDeviceTrackController.getFileUrlsByUserDeviceTrackId(userDeviceTrackId);

        assertEquals(mockFiles, response.getData());
        verify(userDeviceTrackService, times(1)).getFileUrlsByUserDeviceTrackId(userDeviceTrackId);
    }

    @Test
    void testUpdateUserDeviceTrack() throws Exception {
        Long id = 1L;
        UserDeviceTrackRequest request = new UserDeviceTrackRequest();
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()));
        UserDeviceTrackResponse updatedResponse = new UserDeviceTrackResponse();

        when(userDeviceTrackService.updateUserDeviceTrack(id, request, files)).thenReturn(updatedResponse);

        ResponseEntity<ApiResponse<UserDeviceTrackResponse>> response =
                userDeviceTrackController.updateUserDeviceTrack(id, request, files);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedResponse, response.getBody().getData());
        verify(userDeviceTrackService, times(1)).updateUserDeviceTrack(id, request, files);
    }

    @Test
    void testDeleteUserDeviceTrack() {
        Long id = 1L;

        ApiResponse<Void> response = userDeviceTrackController.deleteUserDeviceTrack(id);

        assertEquals("UserDeviceTrack deleted successfully", response.getMessage());
        verify(userDeviceTrackService, times(1)).deleteUserDeviceTrack(id);
    }
}
