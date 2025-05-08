package com.hacof.hackathon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hacof.hackathon.controller.MentorshipController;
import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.service.MentorshipRequestService;
import com.hacof.hackathon.service.MentorshipSessionRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

class MentorshipControllerTest {

    @Mock
    private MentorshipRequestService mentorshipRequestService;

    @Mock
    private MentorshipSessionRequestService mentorshipSessionRequestService;

    @InjectMocks
    private MentorshipController mentorshipController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreateMentorshipRequest() {
        MentorshipRequestDTO dto = new MentorshipRequestDTO();
        dto.setId("1");

        CommonRequest<MentorshipRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorshipRequestService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorshipRequestDTO>> response =
                mentorshipController.createMentorshipRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorshipRequestService, times(1)).create(dto);
    }

    @Test
    void testApproveMentorshipRequest() {
        MentorshipRequestDTO dto = new MentorshipRequestDTO();
        dto.setId("1");

        CommonRequest<MentorshipRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorshipRequestService.approveOrReject(Long.parseLong(dto.getId()), dto))
                .thenReturn(dto);

        ResponseEntity<CommonResponse<MentorshipRequestDTO>> response =
                mentorshipController.approveMentorshipRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorshipRequestService, times(1)).approveOrReject(Long.parseLong(dto.getId()), dto);
    }

    @Test
    void testRejectMentorshipRequest() {
        MentorshipRequestDTO dto = new MentorshipRequestDTO();
        dto.setId("1");

        CommonRequest<MentorshipRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorshipRequestService.approveOrReject(Long.parseLong(dto.getId()), dto))
                .thenReturn(dto);

        ResponseEntity<CommonResponse<MentorshipRequestDTO>> response =
                mentorshipController.rejectMentorshipRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorshipRequestService, times(1)).approveOrReject(Long.parseLong(dto.getId()), dto);
    }

    @Test
    void testGetAllMentorshipRequests() {
        MentorshipRequestDTO dto = new MentorshipRequestDTO();
        dto.setId("1");

        when(mentorshipRequestService.getAll()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> response =
                mentorshipController.getAllMentorshipRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
        verify(mentorshipRequestService, times(1)).getAll();
    }

    @Test
    void testCreateMentorshipSessionRequest() {
        MentorshipSessionRequestDTO dto = new MentorshipSessionRequestDTO();
        dto.setId("1");

        CommonRequest<MentorshipSessionRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(dto);

        when(mentorshipSessionRequestService.create(dto)).thenReturn(dto);

        ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> response =
                mentorshipController.createMentorshipSessionRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getData().getId());
        verify(mentorshipSessionRequestService, times(1)).create(dto);
    }

    @Test
    void testDeleteMentorshipSessionRequest() {
        doNothing().when(mentorshipSessionRequestService).delete("1");

        ResponseEntity<CommonResponse<Void>> response = mentorshipController.deleteMentorshipSessionRequest("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mentorshipSessionRequestService, times(1)).delete("1");
    }
}
