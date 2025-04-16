package com.hacof.identity.controller;

import com.hacof.identity.dto.ApiRequest;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.request.RoleCreateRequest;
import com.hacof.identity.dto.request.RoleUpdateRequest;
import com.hacof.identity.dto.response.RoleResponse;
import com.hacof.identity.service.RoleService;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
class RoleControllerTest {

    @InjectMocks
    RoleController roleController;

    @Mock
    RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole() {
        RoleCreateRequest createRequest = new RoleCreateRequest();
        ApiRequest<RoleCreateRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(createRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        RoleResponse mockResponse = new RoleResponse();
        when(roleService.createRole(createRequest)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<RoleResponse>> response = roleController.createRole(apiRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody().getData());
        verify(roleService, times(1)).createRole(createRequest);
    }

    @Test
    void testGetRoles() {
        List<RoleResponse> roles = List.of(new RoleResponse(), new RoleResponse());
        when(roleService.getRoles()).thenReturn(roles);

        ApiResponse<List<RoleResponse>> response = roleController.getRoles();

        assertNotNull(response);
        assertEquals(roles, response.getData());
        verify(roleService, times(1)).getRoles();
    }

    @Test
    void testGetRole() {
        Long id = 1L;
        RoleResponse mockResponse = new RoleResponse();
        when(roleService.getRole(id)).thenReturn(mockResponse);

        ApiResponse<RoleResponse> response = roleController.getRole(id);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(roleService, times(1)).getRole(id);
    }

    @Test
    void testGetRoleFromToken() {
        String token = "Bearer abc.def.ghi";
        RoleResponse mockResponse = new RoleResponse();
        when(roleService.getRoleFromToken("abc.def.ghi")).thenReturn(mockResponse);

        ApiResponse<RoleResponse> response = roleController.getRoleFromToken(token);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(roleService, times(1)).getRoleFromToken("abc.def.ghi");
    }

    @Test
    void testUpdateRole() {
        Long id = 1L;
        RoleUpdateRequest updateRequest = new RoleUpdateRequest();
        ApiRequest<RoleUpdateRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(updateRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        RoleResponse mockResponse = new RoleResponse();
        when(roleService.updateRole(id, updateRequest)).thenReturn(mockResponse);

        ApiResponse<RoleResponse> response = roleController.updateRole(id, apiRequest);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(roleService, times(1)).updateRole(id, updateRequest);
    }

    @Test
    void testDeleteRole() {
        Long id = 1L;

        ApiResponse<Void> response = roleController.deleteRole(id);

        assertNotNull(response);
        assertEquals("Role has been deleted", response.getMessage());
        verify(roleService, times(1)).deleteRole(id);
    }
}
