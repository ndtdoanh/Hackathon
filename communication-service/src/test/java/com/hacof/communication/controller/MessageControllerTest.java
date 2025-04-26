package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.service.MessageService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @Mock
    MessageService messageService;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @Test
    void testCreateMessage() {
        Long conversationId = 1L;
        MessageRequest messageRequest = new MessageRequest();
        ApiRequest<MessageRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(messageRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        MessageResponse mockResponse = new MessageResponse();
        when(messageService.createMessage(conversationId, messageRequest)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<MessageResponse>> response =
                messageController.createMessage(conversationId, apiRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse, response.getBody().getData());
        assertEquals("Message created successfully", response.getBody().getMessage());
        assertEquals(apiRequest.getRequestId(), response.getBody().getRequestId());
        assertEquals(apiRequest.getRequestDateTime(), response.getBody().getRequestDateTime());
        assertEquals(apiRequest.getChannel(), response.getBody().getChannel());
        verify(messageService, times(1)).createMessage(conversationId, messageRequest);
    }

    @Test
    void testGetMessageById() {
        Long messageId = 1L;
        MessageResponse mockResponse = new MessageResponse();
        when(messageService.getMessageById(messageId)).thenReturn(mockResponse);

        ApiResponse<MessageResponse> response = messageController.getMessageById(messageId);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        assertEquals("Message retrieved successfully", response.getMessage());
        assertEquals("HACOF", response.getChannel());
        assertNotNull(response.getRequestId());
        assertNotNull(response.getRequestDateTime());
        verify(messageService, times(1)).getMessageById(messageId);
    }

    @Test
    void testGetMessagesByConversation() {
        Long conversationId = 1L;
        List<MessageResponse> mockList = List.of(new MessageResponse(), new MessageResponse());
        when(messageService.getMessagesByConversation(conversationId)).thenReturn(mockList);

        ApiResponse<List<MessageResponse>> response = messageController.getMessagesByConversation(conversationId);

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        assertEquals("Get all messages in conversation", response.getMessage());
        assertEquals("HACOF", response.getChannel());
        assertNotNull(response.getRequestId());
        assertNotNull(response.getRequestDateTime());
        verify(messageService, times(1)).getMessagesByConversation(conversationId);
    }

    @Test
    void testHandleWebSocketMessage() {
        Long conversationId = 1L;
        String username = "testUser";
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setId(1L);
        messageRequest.setContent("Test content");

        MessageResponse mockResponse = new MessageResponse();
        when(messageService.getMessageById(messageRequest.getId())).thenReturn(mockResponse);

        messageController.handleWebSocketMessage(messageRequest, conversationId, username);

        String expectedDestination = "/topic/conversations/" + conversationId;
        verify(messageService, times(1)).getMessageById(messageRequest.getId());
        verify(messagingTemplate, times(1)).convertAndSend(eq(expectedDestination), any(MessageResponse.class));
    }
}
