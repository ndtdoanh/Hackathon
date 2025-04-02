package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.service.MentorTeamLimitService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mentor-team-limits")
@RequiredArgsConstructor
@Slf4j
public class MentorTeamLimitController {
    private final MentorTeamLimitService mentorTeamLimitService;

    @PostMapping
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> createMentorTeamLimit(
            @RequestBody @Valid CommonRequest<MentorTeamLimitDTO> request) {
        MentorTeamLimitDTO mentorTeamLimitDTO = mentorTeamLimitService.create(request.getData());
        CommonResponse<MentorTeamLimitDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team limit created successfully"),
                mentorTeamLimitDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> updateMentorTeamLimit(
            @RequestBody @Valid CommonRequest<MentorTeamLimitDTO> request) {
        MentorTeamLimitDTO mentorTeamLimitDTO =
                mentorTeamLimitService.update(Long.parseLong(request.getData().getId()), request.getData());
        CommonResponse<MentorTeamLimitDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team limit updated successfully"),
                mentorTeamLimitDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> deleteMentorTeamLimit(
            @RequestBody CommonRequest<MentorTeamLimitDTO> request) {
        String id = request.getData().getId();
        mentorTeamLimitService.delete(Long.parseLong(id));
        CommonResponse<MentorTeamLimitDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team limit deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MentorTeamLimitDTO>>> getAllMentorTeamLimits() {
        List<MentorTeamLimitDTO> mentorTeamLimits = mentorTeamLimitService.getAll();
        CommonResponse<List<MentorTeamLimitDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all mentor team limits successfully"),
                mentorTeamLimits);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MentorTeamLimitDTO>> getMentorTeamLimitById(@PathVariable Long id) {
        MentorTeamLimitDTO mentorTeamLimit = mentorTeamLimitService.getById(id);
        CommonResponse<MentorTeamLimitDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentor team limit successfully"),
                mentorTeamLimit);
        return ResponseEntity.ok(response);
    }
}
