package com.hacof.identity.controller;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.request.PermissionCreateRequest;
import com.hacof.identity.dto.request.PermissionUpdateRequest;
import com.hacof.identity.dto.response.PermissionResponse;
import com.hacof.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class PermissionControllerTest {

    @InjectMocks
    PermissionController permissionController;

    @Mock
    PermissionService permissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePermission() {
        PermissionCreateRequest createRequest = new PermissionCreateRequest();
        ApiRequest<PermissionCreateRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(createRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        PermissionResponse mockResponse = new PermissionResponse();
        when(permissionService.createPermission(createRequest)).thenReturn(mockResponse);

        ResponseEntity<?> responseEntity = permissionController.createPermission(apiRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(permissionService, times(1)).createPermission(createRequest);
    }

    @Test
    void testGetPermissions() {
        List<PermissionResponse> mockList = new ArrayList<>();
        when(permissionService.getPermissions()).thenReturn(mockList);

        var response = permissionController.getPermissions();

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        verify(permissionService, times(1)).getPermissions();
    }

    @Test
    void testGetPermission() {
        Long id = 1L;
        PermissionResponse mockResponse = new PermissionResponse();
        when(permissionService.getPermission(id)).thenReturn(mockResponse);

        var response = permissionController.getPermission(id);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(permissionService, times(1)).getPermission(id);
    }

    @Test
    void testUpdatePermission() {
        Long id = 1L;
        PermissionUpdateRequest updateRequest = new PermissionUpdateRequest();
        ApiRequest<PermissionUpdateRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(updateRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        PermissionResponse mockResponse = new PermissionResponse();
        when(permissionService.updatePermission(id, updateRequest)).thenReturn(mockResponse);

        var response = permissionController.updatePermission(id, apiRequest);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(permissionService, times(1)).updatePermission(id, updateRequest);
    }

    @Test
    void testDeletePermission() {
        Long id = 1L;

        var response = permissionController.deletePermission(id);

        assertNotNull(response);
        assertEquals("Permission has been deleted", response.getMessage());
        verify(permissionService, times(1)).deletePermission(id);
    }

    @Test
    void testDeletePermissionFromRole() {
        Long roleId = 1L;
        Long permissionId = 2L;

        var response = permissionController.deletePermissionFromRole(roleId, permissionId);

        assertNotNull(response);
        assertEquals("Permission has been removed from role", response.getMessage());
        verify(permissionService, times(1)).deletePermissionFromRole(roleId, permissionId);
    }
}
