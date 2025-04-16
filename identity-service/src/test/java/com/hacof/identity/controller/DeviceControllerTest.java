package com.hacof.identity.controller;

import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.service.DeviceService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class DeviceControllerTest {

    @Mock
    DeviceService deviceService;

    @InjectMocks
    DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDevice() throws IOException {
        DeviceRequest deviceRequest = new DeviceRequest();
        DeviceResponse mockResponse = new DeviceResponse();

        when(deviceService.createDevice(any(DeviceRequest.class), anyList())).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<DeviceResponse>> response = deviceController.createDevice(deviceRequest, new ArrayList<>());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody().getData());
        verify(deviceService, times(1)).createDevice(any(DeviceRequest.class), anyList());
    }

    @Test
    void testGetAllDevices() {
        List<DeviceResponse> mockDevices = Collections.singletonList(new DeviceResponse());
        when(deviceService.getDevices()).thenReturn(mockDevices);

        ApiResponse<List<DeviceResponse>> response = deviceController.getAllDevices();

        assertEquals(mockDevices, response.getData());
        verify(deviceService, times(1)).getDevices();
    }

    @Test
    void testGetDeviceById() {
        Long deviceId = 1L;
        DeviceResponse mockResponse = new DeviceResponse();
        when(deviceService.getDevice(deviceId)).thenReturn(mockResponse);

        ApiResponse<DeviceResponse> response = deviceController.getDeviceById(deviceId);

        assertEquals(mockResponse, response.getData());
        verify(deviceService, times(1)).getDevice(deviceId);
    }

    @Test
    void testGetDevicesByHackathonId() {
        String hackathonId = "hackathon123";
        List<DeviceResponse> mockDevices = Collections.singletonList(new DeviceResponse());
        when(deviceService.getDevicesByHackathonId(hackathonId)).thenReturn(mockDevices);

        ApiResponse<List<DeviceResponse>> response = deviceController.getDevicesByHackathonId(hackathonId);

        assertEquals(mockDevices, response.getData());
        verify(deviceService, times(1)).getDevicesByHackathonId(hackathonId);
    }

    @Test
    void testGetDevicesByRoundId() {
        String roundId = "round123";
        List<DeviceResponse> mockDevices = Collections.singletonList(new DeviceResponse());
        when(deviceService.getDevicesByRoundId(roundId)).thenReturn(mockDevices);

        ApiResponse<List<DeviceResponse>> response = deviceController.getDevicesByRoundId(roundId);

        assertEquals(mockDevices, response.getData());
        verify(deviceService, times(1)).getDevicesByRoundId(roundId);
    }

    @Test
    void testGetDevicesByRoundLocationId() {
        String roundLocationId = "location123";
        List<DeviceResponse> mockDevices = Collections.singletonList(new DeviceResponse());
        when(deviceService.getDevicesByRoundLocationId(roundLocationId)).thenReturn(mockDevices);

        ApiResponse<List<DeviceResponse>> response = deviceController.getDevicesByRoundLocationId(roundLocationId);

        assertEquals(mockDevices, response.getData());
        verify(deviceService, times(1)).getDevicesByRoundLocationId(roundLocationId);
    }

    @Test
    void testGetFileUrlsByDeviceId() {
        Long deviceId = 1L;
        List<FileUrlResponse> mockFileUrls = Collections.singletonList(new FileUrlResponse());
        when(deviceService.getFileUrlsByDeviceId(deviceId)).thenReturn(mockFileUrls);

        ApiResponse<List<FileUrlResponse>> response = deviceController.getFileUrlsByDeviceId(deviceId);

        assertEquals(mockFileUrls, response.getData());
        verify(deviceService, times(1)).getFileUrlsByDeviceId(deviceId);
    }

    @Test
    void testGetFileUrlById() {
        Long fileId = 1L;
        FileUrlResponse mockResponse = new FileUrlResponse();
        when(deviceService.getFileUrlById(fileId)).thenReturn(mockResponse);

        ApiResponse<FileUrlResponse> response = deviceController.getFileUrlById(fileId);

        assertEquals(mockResponse, response.getData());
        verify(deviceService, times(1)).getFileUrlById(fileId);
    }

    @Test
    void testUpdateDevice() throws IOException {
        Long deviceId = 1L;
        DeviceRequest deviceRequest = new DeviceRequest();
        List<MultipartFile> mockFiles = Collections.emptyList();

        DeviceResponse mockResponse = new DeviceResponse();
        when(deviceService.updateDevice(eq(deviceId), eq(deviceRequest), anyList()))
                .thenReturn(mockResponse);

        ResponseEntity<ApiResponse<DeviceResponse>> response = deviceController.updateDevice(deviceId, deviceRequest, mockFiles);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody().getData());
        verify(deviceService, times(1)).updateDevice(eq(deviceId), eq(deviceRequest), anyList());
    }

    @Test
    void testDeleteDevice() {
        Long deviceId = 1L;
        doNothing().when(deviceService).deleteDevice(deviceId);

        ApiResponse<Void> response = deviceController.deleteDevice(deviceId);

        assertEquals("Device has been deleted", response.getMessage());
        verify(deviceService, times(1)).deleteDevice(deviceId);
    }
}
