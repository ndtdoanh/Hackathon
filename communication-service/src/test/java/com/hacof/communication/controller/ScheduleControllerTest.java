package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.service.ScheduleService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @InjectMocks
    private ScheduleController controller;

    @Mock
    private ScheduleService service;

    private CommonRequest<ScheduleRequestDTO> buildRequest() {
        CommonRequest<ScheduleRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ScheduleRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_NullFields_ShouldSetDefaults() {
        CommonRequest<ScheduleRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ScheduleRequestDTO());

        when(service.createSchedule(any())).thenReturn(new ScheduleResponseDTO());

        ResponseEntity<CommonResponse<ScheduleResponseDTO>> response = controller.createSchedule(request);

        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreateSchedule_Success() {
        when(service.createSchedule(any())).thenReturn(new ScheduleResponseDTO());
        var response = controller.createSchedule(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateSchedule_IllegalArgument() {
        when(service.createSchedule(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createSchedule(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateSchedule_Exception() {
        when(service.createSchedule(any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.createSchedule(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSchedule_Success() {
        when(service.updateSchedule(eq(1L), any())).thenReturn(new ScheduleResponseDTO());
        var response = controller.updateSchedule(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSchedule_IllegalArgument() {
        when(service.updateSchedule(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.updateSchedule(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSchedule_Exception() {
        when(service.updateSchedule(eq(1L), any())).thenThrow(new RuntimeException("Crash"));
        var response = controller.updateSchedule(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSchedule_Success() {
        doNothing().when(service).deleteSchedule(1L);
        var response = controller.deleteSchedule(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSchedule_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteSchedule(1L);
        var response = controller.deleteSchedule(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSchedule_Exception() {
        doThrow(new RuntimeException("Fail")).when(service).deleteSchedule(1L);
        var response = controller.deleteSchedule(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedule_Success() {
        when(service.getSchedule(1L)).thenReturn(new ScheduleResponseDTO());
        var response = controller.getSchedule(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedule_IllegalArgument() {
        when(service.getSchedule(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getSchedule(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedule_Exception() {
        when(service.getSchedule(1L)).thenThrow(new RuntimeException("Crash"));
        var response = controller.getSchedule(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllSchedules_Success() {
        when(service.getAllSchedules()).thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getAllSchedules();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllSchedules_Exception() {
        when(service.getAllSchedules()).thenThrow(new RuntimeException("DB error"));
        var response = controller.getAllSchedules();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamId_Success() {
        when(service.getSchedulesByTeamId(1L)).thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getSchedulesByTeamId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamId_IllegalArgument() {
        when(service.getSchedulesByTeamId(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getSchedulesByTeamId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamId_Exception() {
        when(service.getSchedulesByTeamId(1L)).thenThrow(new RuntimeException("Fail"));
        var response = controller.getSchedulesByTeamId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByCreatedByUsernameAndHackathonId_Success() {
        when(service.getSchedulesByCreatedByUsernameAndHackathonId("user", 2L))
                .thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getSchedulesByCreatedByUsernameAndHackathonId("user", 2L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamAndHackathon_Success() {
        when(service.getSchedulesByTeamIdAndHackathonId(1L, 2L)).thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getSchedulesByTeamIdAndHackathonId(1L, 2L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAdminSchedules_Success() {
        when(service.getAdminSchedules()).thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getAdminSchedules();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetOperatingSchedulesByHackathonId_Success() {
        when(service.getOperatingSchedulesByHackathonId(1L)).thenReturn(List.of(new ScheduleResponseDTO()));
        var response = controller.getOperatingSchedulesByHackathonId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByCreatedByUsernameAndHackathonId_IllegalArgument() {
        when(service.getSchedulesByCreatedByUsernameAndHackathonId(any(), any()))
                .thenThrow(new IllegalArgumentException("Not exist"));
        var response = controller.getSchedulesByCreatedByUsernameAndHackathonId("user", 99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByCreatedByUsernameAndHackathonId_Exception() {
        when(service.getSchedulesByCreatedByUsernameAndHackathonId(any(), any()))
                .thenThrow(new RuntimeException("Fail"));
        var response = controller.getSchedulesByCreatedByUsernameAndHackathonId("user", 1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamIdAndHackathonId_IllegalArgument() {
        when(service.getSchedulesByTeamIdAndHackathonId(any(), any()))
                .thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.getSchedulesByTeamIdAndHackathonId(1L, 1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetSchedulesByTeamIdAndHackathonId_Exception() {
        when(service.getSchedulesByTeamIdAndHackathonId(any(), any())).thenThrow(new RuntimeException("Oops"));
        var response = controller.getSchedulesByTeamIdAndHackathonId(1L, 1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAdminSchedules_Exception() {
        when(service.getAdminSchedules()).thenThrow(new RuntimeException("DB error"));
        var response = controller.getAdminSchedules();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetOperatingSchedulesByHackathonId_Exception() {
        when(service.getOperatingSchedulesByHackathonId(1L)).thenThrow(new RuntimeException("Error"));
        var response = controller.getOperatingSchedulesByHackathonId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }
}
