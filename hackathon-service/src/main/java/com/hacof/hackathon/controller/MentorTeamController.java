package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.hackathon.constant.StatusCode;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.service.MentorTeamLimitService;
import com.hacof.hackathon.service.MentorTeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentor-teams")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class MentorTeamController {
    MentorTeamService mentorTeamService;
    MentorTeamLimitService mentorTeamLimitService;

    // pending
    @PostMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> createMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Creating mentor team: {}", request.getData());
        MentorTeamDTO created = mentorTeamService.create(request.getData());
        return ResponseEntity.ok(createCommonResponse(
                request.getRequestId(),
                request.getChannel(),
                "Mentor team created successfully",
                created
        ));
    }

    // pending
    @PutMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> updateMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Updating mentor team: {}", request.getData().getId());
        MentorTeamDTO updated = mentorTeamService.update(request.getData().getId(), request.getData());
        return ResponseEntity.ok(createCommonResponse(
                request.getRequestId(),
                request.getChannel(),
                "Mentor team updated successfully",
                updated
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_MENTOR_TEAM')")
    public ResponseEntity<CommonResponse<Void>> deleteMentorTeam(@PathVariable String id) {
        mentorTeamService.delete(id);
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Mentor team deleted successfully",
                null
        ));
    }

    @GetMapping("/filter-by-hackathon-and-team")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByHackathonIdAndTeamId(
            @RequestParam String hackathonId, @RequestParam String teamId) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByHackathonIdAndTeamId(hackathonId, teamId);
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Fetched mentor teams successfully",
                results
        ));
    }

    @GetMapping("/filter-by-mentor")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByMentorId(@RequestParam String mentorId) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByMentorId(mentorId);
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Fetched mentor teams successfully",
                results
        ));
    }

    // ---------------- ENDPOINT FOR MENTOR TEAM LIMIT ----------------
    @PostMapping("/limits")
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> createMentorTeamLimit(
            @RequestBody @Valid CommonRequest<MentorTeamLimitDTO> request) {
        MentorTeamLimitDTO mentorTeamLimitDTO = mentorTeamLimitService.create(request.getData());
        return ResponseEntity.ok(createCommonResponse(
                request.getRequestId(),
                request.getChannel(),
                "Mentor team limit created successfully",
                mentorTeamLimitDTO
        ));
    }

    @PutMapping("/limits")
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> updateMentorTeamLimit(
            @RequestBody @Valid CommonRequest<MentorTeamLimitDTO> request) {
        MentorTeamLimitDTO mentorTeamLimitDTO =
                mentorTeamLimitService.update(Long.parseLong(request.getData().getId()), request.getData());
        return ResponseEntity.ok(createCommonResponse(
                request.getRequestId(),
                request.getChannel(),
                "Mentor team limit updated successfully",
                mentorTeamLimitDTO
        ));
    }

    @DeleteMapping("/limits/{id}")
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> deleteMentorTeamLimit(@PathVariable String id) {
        mentorTeamLimitService.delete(Long.parseLong(id));
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Mentor team limit deleted successfully",
                null
        ));
    }

    @GetMapping("/limits")
    public ResponseEntity<CommonResponse<List<MentorTeamLimitDTO>>> getAllMentorTeamLimits() {
        List<MentorTeamLimitDTO> mentorTeamLimits = mentorTeamLimitService.getAll();
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Fetched all mentor team limits successfully",
                mentorTeamLimits
        ));
    }

    @GetMapping("/limits/{id}")
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> getMentorTeamLimitById(@PathVariable Long id) {
        MentorTeamLimitDTO mentorTeamLimit = mentorTeamLimitService.getById(id);
        return ResponseEntity.ok(createCommonResponse(
                UUID.randomUUID().toString(),
                "HACOF",
                "Fetched mentor team limit successfully",
                mentorTeamLimit
        ));
    }

    private <T> CommonResponse<T> createCommonResponse(String requestId, String channel, String message, T data) {
        return new CommonResponse<>(
                requestId,
                LocalDateTime.now(),
                channel,
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), message),
                data
        );
    }
}
