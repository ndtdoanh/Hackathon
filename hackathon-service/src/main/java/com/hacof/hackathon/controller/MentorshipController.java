package com.hacof.hackathon.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.service.MentorshipRequestService;
import com.hacof.hackathon.service.MentorshipSessionRequestService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentorship")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorshipController {
    MentorshipRequestService mentorshipRequestService;
    MentorshipSessionRequestService mentorshipSessionRequestService;

    // --- ENDPOINTS FOR MENTORSHIP REQUESTS ---
    @PostMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> createMentorshipRequest(
            @RequestBody @Valid CommonRequest<MentorshipRequestDTO> request) {
        MentorshipRequestDTO mentorshipRequestDTO = mentorshipRequestService.create(request.getData());
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request created successfully"), mentorshipRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> updateMentorshipRequest(
            @RequestBody @Valid CommonRequest<MentorshipRequestDTO> request) {
        MentorshipRequestDTO mentorshipRequestDTO =
                mentorshipRequestService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request updated successfully"), mentorshipRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> deleteMentorshipRequest(
            @RequestBody CommonRequest<MentorshipRequestDTO> request) {
        String id = request.getData().getId();
        mentorshipRequestService.delete(Long.parseLong(id));
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship request deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getAllMentorshipRequests() {
        List<MentorshipRequestDTO> mentorshipRequests = mentorshipRequestService.getAll();
        CommonResponse<List<MentorshipRequestDTO>> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched all mentorship requests successfully"), mentorshipRequests);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MentorshipRequestDTO>> getMentorshipRequestById(@PathVariable Long id) {
        MentorshipRequestDTO mentorshipRequest = mentorshipRequestService.getById(id);
        CommonResponse<MentorshipRequestDTO> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship request successfully"), mentorshipRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-team-and-hackathon")
    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getAllByTeamIdAndHackathonId(
            @RequestParam String teamId, @RequestParam String hackathonId) {
        List<MentorshipRequestDTO> requests =
                mentorshipRequestService.getAllByTeamIdAndHackathonId(teamId, hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship requests successfully"), requests));
    }

    @GetMapping("/filter-by-mentor")
    public ResponseEntity<CommonResponse<List<MentorshipRequestDTO>>> getAllByMentorId(@RequestParam String mentorId) {
        List<MentorshipRequestDTO> requests = mentorshipRequestService.getAllByMentorId(mentorId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship requests successfully"), requests));
    }

    @GetMapping("/sessions/filter-by-mentor-team")
    public ResponseEntity<CommonResponse<List<MentorshipSessionRequestDTO>>> getAllByMentorTeamId(
            @RequestParam String mentorTeamId) {
        List<MentorshipSessionRequestDTO> requests = mentorshipSessionRequestService.getAllByMentorTeamId(mentorTeamId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship session requests successfully"), requests));
    }

    // --- ENDPOINTS FOR MENTORSHIP SESSION REQUESTS ---
    @PostMapping("/sessions")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> createMentorshipSessionRequest(
            @Valid @RequestBody CommonRequest<MentorshipSessionRequestDTO> request) {
        log.debug("Creating mentorship session request: {}", request.getData());
        MentorshipSessionRequestDTO created = mentorshipSessionRequestService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship session request created successfully"), created));
    }

    @PutMapping("/sessions")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> updateMentorshipSessionRequest(
            @Valid @RequestBody CommonRequest<MentorshipSessionRequestDTO> request) {
        log.debug("Updating mentorship session request: {}", request.getData().getId());
        MentorshipSessionRequestDTO updated =
                mentorshipSessionRequestService.update(request.getData().getId(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentorship session request updated successfully"), updated));
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<CommonResponse<Void>> deleteMentorshipSessionRequest(
            @RequestBody CommonRequest<String> request) {
        log.debug("Deleting mentorship session request: {}", request.getData());
        mentorshipSessionRequestService.delete(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Mentorship session request deleted successfully"), null));
    }

    @GetMapping("/sessions")
    public ResponseEntity<CommonResponse<List<MentorshipSessionRequestDTO>>> getAllMentorshipSessionRequests() {
        List<MentorshipSessionRequestDTO> requests = mentorshipSessionRequestService.getAll();
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched all mentorship session requests successfully"), requests));
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<CommonResponse<MentorshipSessionRequestDTO>> getMentorshipSessionRequestById(
            @PathVariable String id) {
        MentorshipSessionRequestDTO request = mentorshipSessionRequestService.getById(id);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship session request successfully"), request));
    }

    @PostMapping("/sessions/filter-by-mentor-team")
    public ResponseEntity<CommonResponse<List<MentorshipSessionRequestDTO>>> getAllByMentorTeamId(
            @RequestBody CommonRequest<MentorshipSessionRequestDTO> request) {
        List<MentorshipSessionRequestDTO> requests = mentorshipSessionRequestService.getAllByMentorTeamId(
                request.getData().getMentorTeamId());
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentorship session requests successfully"), requests));
    }
}
