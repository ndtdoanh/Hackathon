package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.hacof.communication.dto.request.ForumThreadMemberRequestDTO;
import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.service.ForumThreadService;
import com.hacof.communication.util.CommonRequest;

@ExtendWith(MockitoExtension.class)
class ForumThreadControllerTest {

    @InjectMocks
    private ForumThreadController controller;

    @Mock
    private ForumThreadService service;

    private CommonRequest<ForumThreadRequestDTO> buildAdminRequest() {
        CommonRequest<ForumThreadRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ForumThreadRequestDTO());
        return request;
    }

    private CommonRequest<ForumThreadMemberRequestDTO> buildMemberRequest() {
        CommonRequest<ForumThreadMemberRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ForumThreadMemberRequestDTO());
        return request;
    }

    @Test
    void testCreateForumThreadByAdmin_Success() {
        when(service.createForumThread(any())).thenReturn(new ForumThreadResponseDTO());
        var response = controller.createForumThreadByAdmin(buildAdminRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateForumThreadByAdmin_IllegalArgument() {
        when(service.createForumThread(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createForumThreadByAdmin(buildAdminRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateForumThreadByAdmin_Exception() {
        when(service.createForumThread(any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.createForumThreadByAdmin(buildAdminRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testCreateForumThreadByMember_Success() {
        when(service.createForumThreadByMember(any())).thenReturn(new ForumThreadResponseDTO());
        var response = controller.createForumThreadByMember(buildMemberRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateForumThreadByMember_IllegalArgument() {
        when(service.createForumThreadByMember(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createForumThreadByMember(buildMemberRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateForumThreadByMember_Exception() {
        when(service.createForumThreadByMember(any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.createForumThreadByMember(buildMemberRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllForumThreads_Success() {
        when(service.getAllForumThreads()).thenReturn(List.of(new ForumThreadResponseDTO()));
        var response = controller.getAllForumThreads();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllForumThreads_Exception() {
        when(service.getAllForumThreads()).thenThrow(new RuntimeException("Error"));
        var response = controller.getAllForumThreads();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetForumThreadById_Success() {
        when(service.getForumThread(1L)).thenReturn(new ForumThreadResponseDTO());
        var response = controller.getForumThreadById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetForumThreadById_IllegalArgument() {
        when(service.getForumThread(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getForumThreadById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetForumThreadById_Exception() {
        when(service.getForumThread(1L)).thenThrow(new RuntimeException("Crashed"));
        var response = controller.getForumThreadById(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByAdmin_Success() {
        when(service.updateForumThread(eq(1L), any())).thenReturn(new ForumThreadResponseDTO());
        var response = controller.updateForumThreadByAdmin(1L, buildAdminRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByAdmin_IllegalArgument() {
        when(service.updateForumThread(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.updateForumThreadByAdmin(1L, buildAdminRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByAdmin_Exception() {
        when(service.updateForumThread(eq(1L), any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.updateForumThreadByAdmin(1L, buildAdminRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByMember_Success() {
        when(service.updateForumThreadByMember(eq(1L), any())).thenReturn(new ForumThreadResponseDTO());
        var response = controller.updateForumThreadByMember(1L, buildMemberRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByMember_IllegalArgument() {
        when(service.updateForumThreadByMember(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.updateForumThreadByMember(1L, buildMemberRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateForumThreadByMember_Exception() {
        when(service.updateForumThreadByMember(eq(1L), any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.updateForumThreadByMember(1L, buildMemberRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteForumThread_Success() {
        doNothing().when(service).deleteForumThread(1L);
        var response = controller.deleteForumThread(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Forum thread deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteForumThread_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteForumThread(1L);
        var response = controller.deleteForumThread(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteForumThread_Exception() {
        doThrow(new RuntimeException("Crashed")).when(service).deleteForumThread(1L);
        var response = controller.deleteForumThread(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetForumThreadsByCategoryId_Success() {
        when(service.getForumThreadsByCategoryId(1L)).thenReturn(List.of(new ForumThreadResponseDTO()));
        var response = controller.getForumThreadsByCategoryId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetForumThreadsByCategoryId_IllegalArgument() {
        when(service.getForumThreadsByCategoryId(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getForumThreadsByCategoryId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetForumThreadsByCategoryId_Exception() {
        when(service.getForumThreadsByCategoryId(1L)).thenThrow(new RuntimeException("Boom"));
        var response = controller.getForumThreadsByCategoryId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testSetCommonResponseFields_NullFields_ShouldSetDefaults() {
        CommonRequest<ForumThreadRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ForumThreadRequestDTO());

        when(service.createForumThread(any())).thenReturn(new ForumThreadResponseDTO());

        var response = controller.createForumThreadByAdmin(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }
}
