package com.hacof.submission;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.hacof.submission.controller.SubmissionController;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.service.SubmissionService;
import com.hacof.submission.util.CommonResponse;

class SubmissionControllerTest {

    @Mock
    private SubmissionService service;

    @InjectMocks
    private SubmissionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubmission_Success() throws IOException {
        SubmissionResponseDTO dto = new SubmissionResponseDTO();
        when(service.createSubmission(any(), any())).thenReturn(dto);

        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.createSubmission(List.of(mockFile), "1", "2", "SUBMITTED");

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testCreateSubmission_MissingIds() {
        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.createSubmission(null, "", "", "SUBMITTED");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(
                "Round ID and Team ID must not be null or empty.",
                response.getBody().getMessage());
    }

    @Test
    void testCreateSubmission_IOException() throws IOException {
        when(service.createSubmission(any(), any())).thenThrow(new IOException("Disk full"));

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.createSubmission(null, "1", "2", "SUBMITTED");

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Disk full"));
    }

    @Test
    void testCreateSubmission_Exception() throws IOException {
        when(service.createSubmission(any(), any())).thenThrow(new RuntimeException("Server crash"));

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.createSubmission(null, "1", "2", "SUBMITTED");

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionById_Success() {
        when(service.getSubmissionById(1L)).thenReturn(new SubmissionResponseDTO());

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response = controller.getSubmissionById(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionById_NotFound() {
        when(service.getSubmissionById(1L)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response = controller.getSubmissionById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllSubmissions_Success() {
        when(service.getAllSubmissions()).thenReturn(List.of(new SubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response = controller.getAllSubmissions();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllSubmissions_Exception() {
        when(service.getAllSubmissions()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response = controller.getAllSubmissions();

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSubmission_Success() throws Exception {
        when(service.updateSubmission(eq(1L), any(), any())).thenReturn(new SubmissionResponseDTO());

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.updateSubmission(1L, null, 2L, 3L, "UPDATED");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSubmission_IllegalArgument() throws Exception {
        when(service.updateSubmission(eq(1L), any(), any())).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.updateSubmission(1L, null, 2L, 3L, "ERROR");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSubmission_Exception() throws Exception {
        when(service.updateSubmission(eq(1L), any(), any())).thenThrow(new RuntimeException("System crash"));

        ResponseEntity<CommonResponse<SubmissionResponseDTO>> response =
                controller.updateSubmission(1L, null, 2L, 3L, "CRASH");

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSubmission_Success() {
        when(service.deleteSubmission(1L)).thenReturn(true);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteSubmission(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Submission deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteSubmission_NotFound() {
        doThrow(new RuntimeException("Not found")).when(service).deleteSubmission(1L);

        ResponseEntity<CommonResponse<Void>> response = controller.deleteSubmission(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Submission not found!", response.getBody().getMessage());
    }

    @Test
    void testGetSubmissionsByRoundAndCreatedBy_Success() {
        when(service.getSubmissionsByRoundAndCreatedBy(1L, "alice")).thenReturn(List.of(new SubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByRoundAndCreatedBy(1L, "alice");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionsByRoundAndCreatedBy_IllegalArgument() {
        when(service.getSubmissionsByRoundAndCreatedBy(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByRoundAndCreatedBy(1L, "x");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionsByRoundAndCreatedBy_Exception() {
        when(service.getSubmissionsByRoundAndCreatedBy(anyLong(), anyString()))
                .thenThrow(new RuntimeException("Internal"));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByRoundAndCreatedBy(1L, "x");

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionsByTeamAndRound_Success() {
        when(service.getSubmissionsByTeamAndRound(1L, 2L))
                .thenReturn(Collections.singletonList(new SubmissionResponseDTO()));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByTeamAndRound(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionsByTeamAndRound_IllegalArgument() {
        when(service.getSubmissionsByTeamAndRound(1L, 2L)).thenThrow(new IllegalArgumentException("Bad request"));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByTeamAndRound(1L, 2L);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetSubmissionsByTeamAndRound_Exception() {
        when(service.getSubmissionsByTeamAndRound(1L, 2L)).thenThrow(new RuntimeException("Fail"));

        ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> response =
                controller.getSubmissionsByTeamAndRound(1L, 2L);

        assertEquals(500, response.getStatusCodeValue());
    }
}
