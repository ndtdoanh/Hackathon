package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.service.MentorshipRequestService;
import com.hacof.hackathon.service.MentorshipSessionRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentorship")
@RequiredArgsConstructor
@Slf4j
public class MentorshipController {
    private final MentorshipRequestService mentorshipRequestService;
    private final MentorshipSessionRequestService mentorshipSessionRequestService;

    //    @PostMapping("/request")
    //    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> requestMentor(
    //            @Valid @RequestBody CommonRequest<MentorshipRequestDTO> request) {
    //        log.debug("Received request to request mentor: {}", request);
    //        MentorshipRequestDTO result = mentorshipRequestService.requestMentor(
    //                Long.parseLong(request.getData().getTeamId()),
    //                Long.parseLong(request.getData().getMentorId()));
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentor requested successfully"),
    //                result));
    //    }
    //
    //    @PostMapping("/approve")
    //    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> approveRequest(
    //            @Valid @RequestBody CommonRequest<Long> request) {
    //        log.debug("Received request to approve mentorship request: {}", request);
    //        MentorshipRequestDTO result = mentorshipRequestService.approveRequest(request.getData(),
    // request.getData());
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentorship request approved successfully"),
    //                result));
    //    }
    //
    //    @PostMapping("/reject")
    //    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> rejectRequest(
    //            @Valid @RequestBody CommonRequest<Long> request) {
    //        log.debug("Received request to reject mentorship request: {}", request);
    //        MentorshipRequestDTO result = mentorshipRequestService.rejectRequest(request.getData(),
    // request.getData());
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentorship request rejected successfully"),
    //                result));
    //    }

    //    @GetMapping("/team/{teamId}")
    //    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getRequestsByTeam(@PathVariable Long teamId)
    // {
    //        log.debug("Received request to get mentorship requests by team: {}", teamId);
    //        List<MentorshipRequestDTO> result = mentorshipRequestService.getRequestsByTeam(teamId);
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched mentorship requests successfully"),
    //                result));
    //    }
    //
    //    @PostMapping("/sessions/request")
    //    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> requestSession(
    //            @Valid @RequestBody CommonRequest<MentorshipSessionRequestDTO> request) {
    //        log.debug("Received request to request mentorship session: {}", request);
    //        MentorshipSessionRequestDTO result = mentorshipSessionRequestService.requestSession(
    //                request.getData().getMentorTeamId(),
    //                request.getData().getStartTime(),
    //                request.getData().getEndTime(),
    //                request.getData().getLocation(),
    //                request.getData().getDescription());
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentorship session requested successfully"),
    //                result));
    //    }
    //
    //    @PostMapping("/sessions/approve")
    //    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> approveSession(
    //            @Valid @RequestBody CommonRequest<Long> request) {
    //        log.debug("Received request to approve mentorship session: {}", request);
    //        MentorshipSessionRequestDTO result =
    //                mentorshipSessionRequestService.approveSession(request.getData(), request.getData());
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentorship session approved successfully"),
    //                result));
    //    }
    //
    //    @PostMapping("/sessions/reject")
    //    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> rejectSession(
    //            @Valid @RequestBody CommonRequest<Long> request) {
    //        log.debug("Received request to reject mentorship session: {}", request);
    //        MentorshipSessionRequestDTO result =
    //                mentorshipSessionRequestService.rejectSession(request.getData(), request.getData());
    //        return ResponseEntity.ok(new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Mentorship session rejected successfully"),
    //                result));
    //    }

    @PostMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> createMentorshipRequest(
            @RequestBody @Valid CommonRequest<MentorshipRequestDTO> request) {
        MentorshipRequestDTO mentorshipRequestDTO = mentorshipRequestService.create(request.getData());
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request created successfully"),
                mentorshipRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> updateMentorshipRequest(
            @RequestBody @Valid CommonRequest<MentorshipRequestDTO> request) {
        MentorshipRequestDTO mentorshipRequestDTO =
                mentorshipRequestService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request updated successfully"),
                mentorshipRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> deleteMentorshipRequest(
            @RequestBody @Valid CommonRequest<MentorshipRequestDTO> request) {
        String id = request.getData().getId();
        mentorshipRequestService.delete(Long.parseLong(id));
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getAllMentorshipRequests() {
        List<MentorshipRequestDTO> mentorshipRequests = mentorshipRequestService.getAll();
        CommonResponse<List<MentorshipRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all mentorship requests successfully"),
                mentorshipRequests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> getMentorshipRequestById(@PathVariable Long id) {
        MentorshipRequestDTO mentorshipRequest = mentorshipRequestService.getById(id);
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship request successfully"),
                mentorshipRequest);
        return ResponseEntity.ok(response);
    }
}
