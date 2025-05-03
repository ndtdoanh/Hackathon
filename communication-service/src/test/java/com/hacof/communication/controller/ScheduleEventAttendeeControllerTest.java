package com.hacof.communication.controller;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.service.ScheduleEventAttendeeService;
import com.hacof.communication.util.CommonRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleEventAttendeeControllerTest {

    @InjectMocks
    private ScheduleEventAttendeeController controller;

    @Mock
    private ScheduleEventAttendeeService service;

    private CommonRequest<ScheduleEventAttendeeRequestDTO> buildRequest() {
        var req = new CommonRequest<ScheduleEventAttendeeRequestDTO>();
        req.setRequestId(null);
        req.setRequestDateTime(null);
        req.setChannel(null);
        req.setData(new ScheduleEventAttendeeRequestDTO());
        return req;
    }

    @Test
    void testCoverage_setRequestId_withNull() {
        var request = new CommonRequest<ScheduleEventAttendeeRequestDTO>();
        request.setRequestId(null);
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ScheduleEventAttendeeRequestDTO());

        when(service.createScheduleEventAttendee(any())).thenReturn(new ScheduleEventAttendeeResponseDTO());

        var response = controller.createScheduleEventAttendee(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
    }

    @Test
    void testSetRequestId_NotNull_ShouldUseProvidedId() {
        var requestId = UUID.randomUUID().toString();

        var req = new CommonRequest<ScheduleEventAttendeeRequestDTO>();
        req.setRequestId(requestId);
        req.setRequestDateTime(LocalDateTime.now());
        req.setChannel("ZALO");
        req.setData(new ScheduleEventAttendeeRequestDTO());

        when(service.createScheduleEventAttendee(any()))
                .thenReturn(new ScheduleEventAttendeeResponseDTO());

        var res = controller.createScheduleEventAttendee(req);

        assertEquals(requestId, res.getBody().getRequestId());
    }


    @Test
    void testCreate_Success() {
        when(service.createScheduleEventAttendee(any())).thenReturn(new ScheduleEventAttendeeResponseDTO());
        var response = controller.createScheduleEventAttendee(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreate_IllegalArgument() {
        when(service.createScheduleEventAttendee(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createScheduleEventAttendee(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        when(service.createScheduleEventAttendee(any())).thenThrow(new RuntimeException("Error"));
        var response = controller.createScheduleEventAttendee(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        when(service.updateScheduleEventAttendee(eq(1L), any()))
                .thenReturn(new ScheduleEventAttendeeResponseDTO());
        var response = controller.updateScheduleEventAttendee(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_IllegalArgument() {
        when(service.updateScheduleEventAttendee(eq(1L), any()))
                .thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.updateScheduleEventAttendee(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        when(service.updateScheduleEventAttendee(eq(1L), any()))
                .thenThrow(new RuntimeException("Crash"));
        var response = controller.updateScheduleEventAttendee(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(service).deleteScheduleEventAttendee(1L);
        var response = controller.deleteScheduleEventAttendee(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDelete_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteScheduleEventAttendee(1L);
        var response = controller.deleteScheduleEventAttendee(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        doThrow(new RuntimeException("Fail")).when(service).deleteScheduleEventAttendee(1L);
        var response = controller.deleteScheduleEventAttendee(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Success() {
        when(service.getScheduleEventAttendee(1L)).thenReturn(List.of(new ScheduleEventAttendeeResponseDTO()));
        var response = controller.getScheduleEventAttendee(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Exception() {
        when(service.getScheduleEventAttendee(1L)).thenThrow(new RuntimeException("Error"));
        var response = controller.getScheduleEventAttendee(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAll_Success() {
        when(service.getAllScheduleEventAttendees()).thenReturn(List.of(new ScheduleEventAttendeeResponseDTO()));
        var response = controller.getAllScheduleEventAttendees();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAll_Exception() {
        when(service.getAllScheduleEventAttendees()).thenThrow(new RuntimeException("Crash"));
        var response = controller.getAllScheduleEventAttendees();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testChangeStatus_Success() {
        when(service.changeStatus(eq(1L), any()))
                .thenReturn(new ScheduleEventAttendeeResponseDTO());
        var response = controller.changeStatus(1L, ScheduleEventStatus.CONFIRMED);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testChangeStatus_IllegalArgument() {
        when(service.changeStatus(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.changeStatus(1L, ScheduleEventStatus.DECLINED);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testChangeStatus_Exception() {
        when(service.changeStatus(eq(1L), any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.changeStatus(1L, ScheduleEventStatus.DECLINED);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByEventId_Success() {
        when(service.getScheduleEventAttendeesByEventId(1L))
                .thenReturn(List.of(new ScheduleEventAttendeeResponseDTO()));
        var response = controller.getScheduleEventAttendeesByEventId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByEventId_IllegalArgument() {
        when(service.getScheduleEventAttendeesByEventId(1L))
                .thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.getScheduleEventAttendeesByEventId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByEventId_Exception() {
        when(service.getScheduleEventAttendeesByEventId(1L))
                .thenThrow(new RuntimeException("Error"));
        var response = controller.getScheduleEventAttendeesByEventId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }
}
