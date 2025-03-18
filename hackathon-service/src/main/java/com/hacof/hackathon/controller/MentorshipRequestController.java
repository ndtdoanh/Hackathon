package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.service.MentorshipRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentorship-requests")
@RequiredArgsConstructor
@Slf4j
public class MentorshipRequestController {
    private final MentorshipRequestService mentorshipRequestService;

    @PostMapping("/request")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> requestMentor(
            @Valid @RequestBody CommonRequest<MentorshipRequestDTO> request) {
        log.debug("Received request to request mentor: {}", request);
        MentorshipRequestDTO result = mentorshipRequestService.requestMentor(
                request.getData().getTeamId(), request.getData().getMentorId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor requested successfully"),
                result));
    }

    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> approveRequest(
            @Valid @RequestBody CommonRequest<Long> request) {
        log.debug("Received request to approve mentorship request: {}", request);
        MentorshipRequestDTO result = mentorshipRequestService.approveRequest(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request approved successfully"),
                result));
    }

    @PostMapping("/reject")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> rejectRequest(
            @Valid @RequestBody CommonRequest<Long> request) {
        log.debug("Received request to reject mentorship request: {}", request);
        MentorshipRequestDTO result = mentorshipRequestService.rejectRequest(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request rejected successfully"),
                result));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getRequestsByTeam(@PathVariable Long teamId) {
        log.debug("Received request to get mentorship requests by team: {}", teamId);
        List<MentorshipRequestDTO> result = mentorshipRequestService.getRequestsByTeam(teamId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship requests successfully"),
                result));
    }
}
