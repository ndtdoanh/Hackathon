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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @Mock
    MessageService messageService;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        verify(messageService, times(2)).getMessagesByConversation(conversationId);
    }

    @Test
    void testHandleWebSocketMessage() {
        Long conversationId = 1L;
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setContent("Test content");

        messageController.handleWebSocketMessage(messageRequest, conversationId);

        String expectedDestination = "/topic/conversations/" + conversationId;
        verify(messagingTemplate, times(1)).convertAndSend(eq(expectedDestination), any(MessageResponse.class));
    }
}
