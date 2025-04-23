package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.hacof.communication.constant.ReactionType;
import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.service.MessageReactionService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
class MessageReactionControllerTest {

    @Mock
    MessageReactionService reactionService;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    MessageReactionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReactToMessage() {
        Long messageId = 1L;
        String requestId = UUID.randomUUID().toString();
        LocalDateTime requestTime = LocalDateTime.now();

        MessageReactionRequest requestData = new MessageReactionRequest();
        MessageReactionResponse expectedResponse = new MessageReactionResponse();

        ApiRequest<MessageReactionRequest> apiRequest = ApiRequest.<MessageReactionRequest>builder()
                .requestId(requestId)
                .requestDateTime(requestTime)
                .channel("HACOF")
                .data(requestData)
                .build();

        when(reactionService.reactToMessage(messageId, requestData)).thenReturn(expectedResponse);

        ResponseEntity<ApiResponse<MessageReactionResponse>> response =
                controller.reactToMessage(messageId, apiRequest);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody().getData());

        verify(reactionService, times(1)).reactToMessage(messageId, requestData);
    }

    @Test
    void testDeleteReaction() {
        Long reactionId = 123L;

        ApiResponse<Void> response = controller.deleteReaction(reactionId);

        assertNotNull(response);
        assertEquals("Reaction has been removed", response.getMessage());

        verify(reactionService, times(1)).removeReaction(reactionId);
    }

    @Test
    void testGetReactionsByMessage() {
        Long messageId = 5L;
        List<MessageReactionResponse> mockList = List.of(new MessageReactionResponse(), new MessageReactionResponse());

        when(reactionService.getReactionsByMessage(messageId)).thenReturn(mockList);

        ApiResponse<List<MessageReactionResponse>> response = controller.getReactionsByMessage(messageId);

        assertNotNull(response);
        assertEquals(mockList, response.getData());

        verify(reactionService, times(1)).getReactionsByMessage(messageId);
    }

    @Test
    void testHandleReaction_validRequest() {
        Long messageId = 1L;
        String username = "testUser";
        MessageReactionRequest request = new MessageReactionRequest();
        request.setId(100L);
        request.setReactionType(ReactionType.LIKE);

        MessageReactionResponse expectedResponse = new MessageReactionResponse();
        expectedResponse.setId("100");
        expectedResponse.setReactionType(ReactionType.LIKE);

        when(reactionService.findById(100L)).thenReturn(expectedResponse);

        controller.handleReaction(request, messageId, username);

        expectedResponse.setCreatedByUserName(username);

        String destination = "/topic/messages";
        verify(messagingTemplate, times(1)).convertAndSend(eq(destination), eq(expectedResponse));

        verify(reactionService, times(1)).findById(100L);
    }

    @Test
    void testHandleReaction_serviceThrowsException() {
        Long messageId = 1L;
        String username = "testUser";

        MessageReactionRequest request = new MessageReactionRequest();
        request.setId(100L);
        request.setReactionType(ReactionType.LIKE);

        when(reactionService.findById(100L)).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> controller.handleReaction(request, messageId, username));

        verify(reactionService, times(1)).findById(100L);
    }
}
