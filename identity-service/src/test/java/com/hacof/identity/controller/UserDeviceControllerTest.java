package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.UserDeviceRequest;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.service.UserDeviceService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserDeviceControllerTest {

    @InjectMocks
    UserDeviceController userDeviceController;

    @Mock
    UserDeviceService userDeviceService;

    @Test
    void testCreateUserDevice() throws IOException {
        UserDeviceRequest request = new UserDeviceRequest();
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()));
        UserDeviceResponse mockResponse = new UserDeviceResponse();

        when(userDeviceService.createUserDevice(request, files)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<UserDeviceResponse>> response =
                userDeviceController.createUserDevice(request, files);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody().getData());
        verify(userDeviceService, times(1)).createUserDevice(request, files);
    }

    @Test
    void testGetUserDevices() {
        List<UserDeviceResponse> mockList = List.of(new UserDeviceResponse(), new UserDeviceResponse());
        when(userDeviceService.getUserDevices()).thenReturn(mockList);

        ApiResponse<List<UserDeviceResponse>> response = userDeviceController.getUserDevices();

        assertEquals(mockList, response.getData());
        verify(userDeviceService, times(1)).getUserDevices();
    }

    @Test
    void testGetUserDevice() {
        Long id = 1L;
        UserDeviceResponse mockResponse = new UserDeviceResponse();
        when(userDeviceService.getUserDevice(id)).thenReturn(mockResponse);

        ApiResponse<UserDeviceResponse> response = userDeviceController.getUserDevice(id);

        assertEquals(mockResponse, response.getData());
        verify(userDeviceService, times(1)).getUserDevice(id);
    }

    @Test
    void testGetUserDevicesByDeviceId() {
        String deviceId = "device123";
        List<UserDeviceResponse> mockList = List.of(new UserDeviceResponse());
        when(userDeviceService.getUserDevicesByDeviceId(deviceId)).thenReturn(mockList);

        ApiResponse<List<UserDeviceResponse>> response = userDeviceController.getUserDevicesByDeviceId(deviceId);

        assertEquals(mockList, response.getData());
        verify(userDeviceService, times(1)).getUserDevicesByDeviceId(deviceId);
    }

    @Test
    void testGetUserDevicesByUserId() {
        String userId = "user123";
        List<UserDeviceResponse> mockList = List.of(new UserDeviceResponse());
        when(userDeviceService.getUserDevicesByUserId(userId)).thenReturn(mockList);

        ApiResponse<List<UserDeviceResponse>> response = userDeviceController.getUserDevicesByUserId(userId);

        assertEquals(mockList, response.getData());
        verify(userDeviceService, times(1)).getUserDevicesByUserId(userId);
    }

    @Test
    void testGetFileUrlsByUserDeviceId() {
        Long userDeviceId = 1L;
        List<FileUrlResponse> fileUrls = List.of(new FileUrlResponse());
        when(userDeviceService.getFileUrlsByUserDeviceId(userDeviceId)).thenReturn(fileUrls);

        ApiResponse<List<FileUrlResponse>> response = userDeviceController.getFileUrlsByUserDeviceId(userDeviceId);

        assertEquals(fileUrls, response.getData());
        verify(userDeviceService, times(1)).getFileUrlsByUserDeviceId(userDeviceId);
    }

    @Test
    void testUpdateUserDevice() throws IOException {
        Long id = 1L;
        UserDeviceRequest request = new UserDeviceRequest();
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()));
        UserDeviceResponse updatedResponse = new UserDeviceResponse();

        when(userDeviceService.updateUserDevice(id, request, files)).thenReturn(updatedResponse);

        ResponseEntity<ApiResponse<UserDeviceResponse>> response =
                userDeviceController.updateUserDevice(id, request, files);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedResponse, response.getBody().getData());
        verify(userDeviceService, times(1)).updateUserDevice(id, request, files);
    }

    @Test
    void testDeleteUserDevice() {
        Long id = 1L;

        ApiResponse<Void> response = userDeviceController.deleteUserDevice(id);

        assertEquals("UserDevice deleted successfully", response.getMessage());
        verify(userDeviceService, times(1)).deleteUserDevice(id);
    }
}
