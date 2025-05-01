package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.service.ScheduleEventReminderService;
import com.hacof.communication.util.CommonRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleEventReminderControllerTest {

    @InjectMocks
    private ScheduleEventReminderController controller;

    @Mock
    private ScheduleEventReminderService service;

    private CommonRequest<ScheduleEventReminderRequestDTO> buildRequest() {
        CommonRequest<ScheduleEventReminderRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ScheduleEventReminderRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_NullValues_Reminder() {
        CommonRequest<ScheduleEventReminderRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ScheduleEventReminderRequestDTO());

        when(service.createScheduleEventReminder(any()))
                .thenReturn(new ScheduleEventReminderResponseDTO());

        var response = controller.createScheduleEventReminder(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateReminder_Success() {
        when(service.createScheduleEventReminder(any())).thenReturn(new ScheduleEventReminderResponseDTO());
        var response = controller.createScheduleEventReminder(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateReminder_IllegalArgument() {
        when(service.createScheduleEventReminder(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createScheduleEventReminder(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateReminder_Exception() {
        when(service.createScheduleEventReminder(any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.createScheduleEventReminder(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateReminder_Success() {
        when(service.updateScheduleEventReminder(eq(1L), any())).thenReturn(new ScheduleEventReminderResponseDTO());
        var response = controller.updateScheduleEventReminder(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateScheduleEventReminder_IllegalArgument() {
        when(service.updateScheduleEventReminder(eq(1L), any()))
                .thenThrow(new IllegalArgumentException("Update failed"));
        var response = controller.updateScheduleEventReminder(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateReminder_Exception() {
        when(service.updateScheduleEventReminder(eq(1L), any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.updateScheduleEventReminder(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteReminder_Success() {
        doNothing().when(service).deleteScheduleEventReminder(1L);
        var response = controller.deleteScheduleEventReminder(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteScheduleEventReminder_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid id"))
                .when(service).deleteScheduleEventReminder(1L);
        var response = controller.deleteScheduleEventReminder(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteReminder_Exception() {
        doThrow(new RuntimeException("Fail")).when(service).deleteScheduleEventReminder(1L);
        var response = controller.deleteScheduleEventReminder(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetReminderById_Success() {
        when(service.getScheduleEventReminder(1L)).thenReturn(List.of(new ScheduleEventReminderResponseDTO()));
        var response = controller.getScheduleEventReminder(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetReminderById_EmptyList() {
        when(service.getScheduleEventReminder(1L)).thenReturn(Collections.emptyList());
        var response = controller.getScheduleEventReminder(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getData().isEmpty());
        assertEquals("ScheduleEventReminder not found!", response.getBody().getMessage());
    }

    @Test
    void testGetReminderById_Exception() {
        when(service.getScheduleEventReminder(1L)).thenThrow(new RuntimeException("Err"));
        var response = controller.getScheduleEventReminder(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllReminders_Success() {
        when(service.getAllScheduleEventReminders()).thenReturn(List.of(new ScheduleEventReminderResponseDTO()));
        var response = controller.getAllScheduleEventReminders();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllScheduleEventReminders_Exception() {
        when(service.getAllScheduleEventReminders())
                .thenThrow(new RuntimeException("Error fetching data"));
        var response = controller.getAllScheduleEventReminders();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByScheduleEventId_Success() {
        when(service.getScheduleEventRemindersByScheduleEventId(1L)).thenReturn(List.of(new ScheduleEventReminderResponseDTO()));
        var response = controller.getScheduleEventRemindersByScheduleEventId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void testGetRemindersByScheduleEventId_IllegalArgument() {
        when(service.getScheduleEventRemindersByScheduleEventId(1L))
                .thenThrow(new IllegalArgumentException("Missing event"));
        var response = controller.getScheduleEventRemindersByScheduleEventId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetRemindersByScheduleEventId_Exception() {
        when(service.getScheduleEventRemindersByScheduleEventId(1L))
                .thenThrow(new RuntimeException("Server error"));
        var response = controller.getScheduleEventRemindersByScheduleEventId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByUserId_Success() {
        when(service.getScheduleEventRemindersByUserId(1L)).thenReturn(List.of(new ScheduleEventReminderResponseDTO()));
        var response = controller.getScheduleEventRemindersByUserId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void testGetRemindersByUserId_IllegalArgument() {
        when(service.getScheduleEventRemindersByUserId(2L))
                .thenThrow(new IllegalArgumentException("Invalid user"));
        var response = controller.getScheduleEventRemindersByUserId(2L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetRemindersByUserId_Exception() {
        when(service.getScheduleEventRemindersByUserId(2L))
                .thenThrow(new RuntimeException("Timeout"));
        var response = controller.getScheduleEventRemindersByUserId(2L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByUserAndScheduleEvent_Success() {
        when(service.getScheduleEventRemindersByUserIdAndScheduleEventId(1L, 2L))
                .thenReturn(List.of(new ScheduleEventReminderResponseDTO()));
        var response = controller.getScheduleEventRemindersByUserIdAndScheduleEventId(1L, 2L);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void testGetRemindersByUserAndEventId_IllegalArgument() {
        when(service.getScheduleEventRemindersByUserIdAndScheduleEventId(anyLong(), anyLong()))
                .thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getScheduleEventRemindersByUserIdAndScheduleEventId(2L, 3L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetRemindersByUserAndEventId_Exception() {
        when(service.getScheduleEventRemindersByUserIdAndScheduleEventId(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Crash"));
        var response = controller.getScheduleEventRemindersByUserIdAndScheduleEventId(2L, 3L);
        assertEquals(500, response.getStatusCodeValue());
    }

}
