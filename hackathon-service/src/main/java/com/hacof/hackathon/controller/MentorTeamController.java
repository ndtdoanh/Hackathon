package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.service.MentorTeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentor-teams")
@RequiredArgsConstructor
@Slf4j
public class MentorTeamController {
    private final MentorTeamService mentorTeamService;

    @PostMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> createMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Creating mentor team: {}", request.getData());
        MentorTeamDTO created = mentorTeamService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team created successfully"),
                created));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> updateMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Updating mentor team: {}", request.getData().getId());
        MentorTeamDTO updated = mentorTeamService.update(request.getData().getId(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team updated successfully"),
                updated));
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteMentorTeam(@Valid @RequestBody CommonRequest<String> request) {
        log.debug("Deleting mentor team: {}", request.getData());
        mentorTeamService.delete(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Mentor team deleted successfully"),
                null));
    }

    @PostMapping("/filter-by-hackathon-and-team")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByHackathonIdAndTeamId(
            @RequestBody CommonRequest<MentorTeamDTO> request) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByHackathonIdAndTeamId(
                request.getData().getHackathonId(), request.getData().getTeamId());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentor teams successfully"),
                results));
    }

    @PostMapping("/filter-by-mentor")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByMentorId(
            @RequestBody CommonRequest<String> request) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByMentorId(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentor teams successfully"),
                results));
    }
}
