package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.request.ThreadPostReportReviewRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.service.ThreadPostReportService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThreadPostReportControllerTest {

    @InjectMocks
    private ThreadPostReportController controller;

    @Mock
    private ThreadPostReportService service;

    private CommonRequest<ThreadPostReportRequestDTO> buildRequest() {
        var req = new CommonRequest<ThreadPostReportRequestDTO>();
        req.setRequestId(UUID.randomUUID().toString());
        req.setRequestDateTime(LocalDateTime.now());
        req.setChannel("HACOF");
        req.setData(new ThreadPostReportRequestDTO());
        return req;
    }

    private CommonRequest<ThreadPostReportReviewRequestDTO> buildReviewRequest() {
        var req = new CommonRequest<ThreadPostReportReviewRequestDTO>();
        req.setRequestId(UUID.randomUUID().toString());
        req.setRequestDateTime(LocalDateTime.now());
        req.setChannel("HACOF");
        req.setData(new ThreadPostReportReviewRequestDTO());
        return req;
    }

    @Test
    void testCreateThreadPostReport_Success() {
        when(service.createThreadPostReport(any())).thenReturn(new ThreadPostReportResponseDTO());
        var response = controller.createThreadPostReport(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateThreadPostReport_IllegalArgument() {
        when(service.createThreadPostReport(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createThreadPostReport(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateThreadPostReport_Exception() {
        when(service.createThreadPostReport(any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.createThreadPostReport(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetReportsByThreadPostId_Success() {
        when(service.getReportsByThreadPostId(1L)).thenReturn(List.of(new ThreadPostReportResponseDTO()));
        var response = controller.getReportsByThreadPostId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetReportsByThreadPostId_Exception() {
        when(service.getReportsByThreadPostId(1L)).thenThrow(new RuntimeException("Error"));
        var response = controller.getReportsByThreadPostId(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostReportById_Success() {
        when(service.getThreadPostReport(1L)).thenReturn(new ThreadPostReportResponseDTO());
        var response = controller.getThreadPostReportById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostReportById_IllegalArgument() {
        when(service.getThreadPostReport(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getThreadPostReportById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostReportById_Exception() {
        when(service.getThreadPostReport(1L)).thenThrow(new RuntimeException("Oops"));
        var response = controller.getThreadPostReportById(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateThreadPostReport_Success() {
        when(service.updateThreadPostReport(eq(1L), any())).thenReturn(new ThreadPostReportResponseDTO());
        var response = controller.updateThreadPostReport(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateThreadPostReport_IllegalArgument() {
        when(service.updateThreadPostReport(eq(1L), any())).thenThrow(new IllegalArgumentException("Fail"));
        var response = controller.updateThreadPostReport(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateThreadPostReport_Exception() {
        when(service.updateThreadPostReport(eq(1L), any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.updateThreadPostReport(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteThreadPostReport_Success() {
        doNothing().when(service).deleteThreadPostReport(5L);
        var response = controller.deleteThreadPostReport(5L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Report deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteThreadPostReport_IllegalArgument() {
        doThrow(new IllegalArgumentException("Not exist")).when(service).deleteThreadPostReport(5L);
        var response = controller.deleteThreadPostReport(5L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteThreadPostReport_Exception() {
        doThrow(new RuntimeException("Failed")).when(service).deleteThreadPostReport(5L);
        var response = controller.deleteThreadPostReport(5L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testReviewThreadPostReport_Success() {
        when(service.reviewThreadPostReport(eq(1L), any())).thenReturn(new ThreadPostReportResponseDTO());
        var response = controller.reviewThreadPostReport(1L, buildReviewRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testReviewThreadPostReport_IllegalArgument() {
        when(service.reviewThreadPostReport(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid review"));
        var response = controller.reviewThreadPostReport(1L, buildReviewRequest());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testReviewThreadPostReport_Exception() {
        when(service.reviewThreadPostReport(eq(1L), any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.reviewThreadPostReport(1L, buildReviewRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllThreadPostReports_Success() {
        when(service.getAllThreadPostReports()).thenReturn(List.of(new ThreadPostReportResponseDTO()));
        var response = controller.getAllThreadPostReports();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testGetAllThreadPostReports_Exception() {
        when(service.getAllThreadPostReports()).thenThrow(new RuntimeException("DB error"));
        var response = controller.getAllThreadPostReports();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testSetCommonResponseFields_NullFieldCoverage() {
        CommonRequest<ThreadPostReportRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ThreadPostReportRequestDTO());

        when(service.createThreadPostReport(any())).thenReturn(new ThreadPostReportResponseDTO());

        var response = controller.createThreadPostReport(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }
}
