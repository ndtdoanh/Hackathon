package com.hacof.communication.controller;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.service.ForumCategoryService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@ExtendWith(MockitoExtension.class)
class ForumCategoryControllerTest {

    @InjectMocks
    private ForumCategoryController controller;

    @Mock
    private ForumCategoryService service;

    private CommonRequest<ForumCategoryRequestDTO> buildRequest() {
        CommonRequest<ForumCategoryRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ForumCategoryRequestDTO());
        return request;
    }

    @Test
    void testCreateForumCategory_RequestFieldsNull_ShouldSetDefaults() {
        CommonRequest<ForumCategoryRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ForumCategoryRequestDTO());

        when(service.createForumCategory(any())).thenReturn(new ForumCategoryResponseDTO());

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response = controller.createForumCategory(request);

        CommonResponse<ForumCategoryResponseDTO> body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateForumCategory_Success() {
        when(service.createForumCategory(any())).thenReturn(new ForumCategoryResponseDTO());

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.createForumCategory(buildRequest());

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateForumCategory_IllegalArgument() {
        when(service.createForumCategory(any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.createForumCategory(buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testCreateForumCategory_Exception() {
        when(service.createForumCategory(any())).thenThrow(new RuntimeException("Boom"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.createForumCategory(buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Boom", response.getBody().getMessage());
    }

    @Test
    void testGetAllForumCategories_Success() {
        when(service.getAllForumCategories()).thenReturn(List.of(new ForumCategoryResponseDTO()));

        ResponseEntity<CommonResponse<List<ForumCategoryResponseDTO>>> response = controller.getAllForumCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllForumCategories_Exception() {
        when(service.getAllForumCategories()).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<CommonResponse<List<ForumCategoryResponseDTO>>> response = controller.getAllForumCategories();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("DB error"));
    }

    @Test
    void testGetForumCategoryById_Success() {
        when(service.getForumCategory(1L)).thenReturn(new ForumCategoryResponseDTO());

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response = controller.getForumCategoryById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetForumCategoryById_IllegalArgument() {
        when(service.getForumCategory(1L)).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response = controller.getForumCategoryById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testGetForumCategoryById_Exception() {
        when(service.getForumCategory(1L)).thenThrow(new RuntimeException("Boom"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response = controller.getForumCategoryById(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Boom", response.getBody().getMessage());
    }

    @Test
    void testUpdateForumCategory_Success() {
        when(service.updateForumCategory(eq(1L), any())).thenReturn(new ForumCategoryResponseDTO());

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.updateForumCategory(1L, buildRequest());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumCategory_IllegalArgument() {
        when(service.updateForumCategory(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.updateForumCategory(1L, buildRequest());

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateForumCategory_Exception() {
        when(service.updateForumCategory(eq(1L), any())).thenThrow(new RuntimeException("Fail"));

        ResponseEntity<CommonResponse<ForumCategoryResponseDTO>> response =
                controller.updateForumCategory(1L, buildRequest());

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Fail", response.getBody().getMessage());
    }

    @Test
    void testDeleteForumCategory_Success() {
        doNothing().when(service).deleteForumCategory(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteForumCategory(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Forum category deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteForumCategory_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteForumCategory(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteForumCategory(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testDeleteForumCategory_Exception() {
        doThrow(new RuntimeException("Crashed")).when(service).deleteForumCategory(1L);

        ResponseEntity<CommonResponse<String>> response = controller.deleteForumCategory(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Crashed", response.getBody().getMessage());
    }
}
