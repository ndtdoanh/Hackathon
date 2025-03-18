package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.service.MentorshipSessionRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentorship-sessions")
@RequiredArgsConstructor
@Slf4j
public class MentorshipSessionRequestController {
    private final MentorshipSessionRequestService mentorshipSessionRequestService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> createSessionRequest(
            @Valid @RequestBody CommonRequest<MentorshipSessionRequestDTO> request) {
        log.debug("Received request to create mentorship session: {}", request);
        MentorshipSessionRequestDTO result = mentorshipSessionRequestService.createSessionRequest(
                request.getData().getMentorshipRequestId(), request.getData().getSessionDetails());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship session created successfully"),
                result));
    }

    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> approveSession(
            @Valid @RequestBody CommonRequest<Long> request) {
        log.debug("Received request to approve mentorship session: {}", request);
        MentorshipSessionRequestDTO result =
                mentorshipSessionRequestService.approveSession(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship session approved successfully"),
                result));
    }

    @PostMapping("/reject")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> rejectSession(
            @Valid @RequestBody CommonRequest<Long> request) {
        log.debug("Received request to reject mentorship session: {}", request);
        MentorshipSessionRequestDTO result =
                mentorshipSessionRequestService.rejectSession(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship session rejected successfully"),
                result));
    }

    @GetMapping("/mentorship/{mentorshipRequestId}")
    public ResponseEntity<CommonResponse<List<MentorshipSessionRequestDTO>>> getSessionsByMentorshipRequest(
            @PathVariable Long mentorshipRequestId) {
        log.debug("Received request to get mentorship sessions by request: {}", mentorshipRequestId);
        List<MentorshipSessionRequestDTO> result =
                mentorshipSessionRequestService.getSessionsByMentorshipRequest(mentorshipRequestId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship sessions successfully"),
                result));
    }
}
