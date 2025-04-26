package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.ConversationRequest;
import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.service.ConversationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ConversationControllerTest {

    @InjectMocks
    ConversationController conversationController;

    @Mock
    ConversationService conversationService;

    @Test
    void testCreateSingleConversation() {
        ConversationRequest requestData = new ConversationRequest();
        ApiRequest<ConversationRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(requestData);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        ConversationResponse mockResponse = new ConversationResponse();
        when(conversationService.createSingleConversation(requestData)).thenReturn(mockResponse);

        ApiResponse<ConversationResponse> apiResponse = conversationController.createSingleConversation(apiRequest);

        assertNotNull(apiResponse);
        assertEquals("Single conversation created successfully", apiResponse.getMessage());
        assertEquals(1000, apiResponse.getCode());
        assertNotNull(apiResponse.getData());
        verify(conversationService, times(1)).createSingleConversation(requestData);
    }

    @Test
    void testGetConversationById() {
        Long id = 1L;
        ConversationResponse mockResponse = new ConversationResponse();
        when(conversationService.getConversationById(id)).thenReturn(mockResponse);

        var response = conversationController.getConversationById(id);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        assertEquals("Conversation retrieved successfully", response.getMessage());
        verify(conversationService, times(1)).getConversationById(id);
    }

    @Test
    void testGetConversationsByUserId() {
        Long userId = 1L;
        List<ConversationResponse> mockList = List.of(new ConversationResponse());
        when(conversationService.getConversationsByUserId(userId)).thenReturn(mockList);

        var response = conversationController.getConversationsByUserId(userId);

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        assertEquals("User conversations retrieved successfully", response.getMessage());
        verify(conversationService, times(1)).getConversationsByUserId(userId);
    }

    @Test
    void testDeleteConversation() {
        Long id = 1L;

        var response = conversationController.deleteConversation(id);

        assertNotNull(response);
        assertEquals("Conversation deleted successfully", response.getMessage());
        verify(conversationService, times(1)).deleteConversation(id);
    }
}
