package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.service.ScheduleEventService;
import com.hacof.communication.util.CommonRequest;

@ExtendWith(MockitoExtension.class)
class ScheduleEventControllerTest {

    @InjectMocks
    private ScheduleEventController controller;

    @Mock
    private ScheduleEventService service;

    private CommonRequest<ScheduleEventRequestDTO> buildRequest() {
        CommonRequest<ScheduleEventRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ScheduleEventRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_NullFields_ShouldSetDefaults() {
        CommonRequest<ScheduleEventRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ScheduleEventRequestDTO());

        when(service.createScheduleEvent(any())).thenReturn(new ScheduleEventResponseDTO());

        var response = controller.createScheduleEvent(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateScheduleEvent_Success() {
        when(service.createScheduleEvent(any())).thenReturn(new ScheduleEventResponseDTO());
        var response = controller.createScheduleEvent(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateScheduleEvent_Exception() {
        when(service.createScheduleEvent(any())).thenThrow(new RuntimeException("Error"));
        var response = controller.createScheduleEvent(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventWithoutFiles_Success() {
        when(service.updateScheduleEventWithoutFiles(eq(1L), any())).thenReturn(new ScheduleEventResponseDTO());
        var response = controller.updateScheduleEventWithoutFiles(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventFiles_Success() {
        when(service.updateScheduleEventFiles(eq(1L), any())).thenReturn(new ScheduleEventResponseDTO());
        var response = controller.updateScheduleEventFiles(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteScheduleEvent_Success() {
        doNothing().when(service).deleteScheduleEvent(1L);
        var response = controller.deleteScheduleEvent(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteScheduleEvent_Exception() {
        doThrow(new RuntimeException("Failed")).when(service).deleteScheduleEvent(1L);
        var response = controller.deleteScheduleEvent(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEvent_Success() {
        when(service.getScheduleEvent(1L)).thenReturn(new ScheduleEventResponseDTO());
        var response = controller.getScheduleEvent(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllScheduleEvents_Success() {
        when(service.getAllScheduleEvents()).thenReturn(List.of(new ScheduleEventResponseDTO()));
        var response = controller.getAllScheduleEvents();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEventsByScheduleId_Success() {
        when(service.getScheduleEventsByScheduleId(1L)).thenReturn(List.of(new ScheduleEventResponseDTO()));
        var response = controller.getScheduleEventsByScheduleId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetFileUrlsByScheduleEventId_Success() {
        when(service.getFileUrlsByScheduleEventId(1L)).thenReturn(List.of(new FileUrlResponse()));
        var response = controller.getFileUrlsByScheduleEventId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateScheduleEvent_IllegalArgument() {
        when(service.createScheduleEvent(any())).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.createScheduleEvent(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testUpdateScheduleEventWithoutFiles_IllegalArgument() {
        when(service.updateScheduleEventWithoutFiles(eq(1L), any()))
                .thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.updateScheduleEventWithoutFiles(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventWithoutFiles_Exception() {
        when(service.updateScheduleEventWithoutFiles(eq(1L), any()))
                .thenThrow(new RuntimeException("Unexpected error"));
        var response = controller.updateScheduleEventWithoutFiles(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventFiles_IllegalArgument() {
        when(service.updateScheduleEventFiles(eq(1L), any())).thenThrow(new IllegalArgumentException("Missing file"));
        var response = controller.updateScheduleEventFiles(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventFiles_Exception() {
        when(service.updateScheduleEventFiles(eq(1L), any())).thenThrow(new RuntimeException("Something went wrong"));
        var response = controller.updateScheduleEventFiles(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteScheduleEvent_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid id")).when(service).deleteScheduleEvent(1L);
        var response = controller.deleteScheduleEvent(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEvent_IllegalArgument() {
        when(service.getScheduleEvent(1L)).thenThrow(new IllegalArgumentException("No match"));
        var response = controller.getScheduleEvent(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEvent_Exception() {
        when(service.getScheduleEvent(1L)).thenThrow(new RuntimeException("Failure"));
        var response = controller.getScheduleEvent(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllScheduleEvents_Exception() {
        when(service.getAllScheduleEvents()).thenThrow(new RuntimeException("DB failed"));
        var response = controller.getAllScheduleEvents();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEventsByScheduleId_IllegalArgument() {
        when(service.getScheduleEventsByScheduleId(1L)).thenThrow(new IllegalArgumentException("Bad input"));
        var response = controller.getScheduleEventsByScheduleId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduleEventsByScheduleId_Exception() {
        when(service.getScheduleEventsByScheduleId(1L)).thenThrow(new RuntimeException("Internal"));
        var response = controller.getScheduleEventsByScheduleId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }
}
