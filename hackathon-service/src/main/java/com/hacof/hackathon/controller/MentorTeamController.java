package com.hacof.hackathon.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.MentorTeamDTO;
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

    @PostMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> createMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Creating mentor team: {}", request.getData());
        MentorTeamDTO created = mentorTeamService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team created successfully"), created));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MentorTeamDTO>> updateMentorTeam(
            @Valid @RequestBody CommonRequest<MentorTeamDTO> request) {
        log.debug("Updating mentor team: {}", request.getData().getId());
        MentorTeamDTO updated = mentorTeamService.update(request.getData().getId(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor team updated successfully"), updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteMentorTeam(@PathVariable String id) {
        mentorTeamService.delete(id);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Mentor team deleted successfully"), null));
    }

    @PostMapping("/filter-by-hackathon-and-team")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByHackathonIdAndTeamId(
            @RequestParam String hackathonId, @RequestParam String teamId) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByHackathonIdAndTeamId(hackathonId, teamId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentor teams successfully"), results));
    }

    @PostMapping("/filter-by-mentor")
    public ResponseEntity<CommonResponse<List<MentorTeamDTO>>> getAllByMentorId(@RequestParam String mentorId) {
        List<MentorTeamDTO> results = mentorTeamService.getAllByMentorId(mentorId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched mentor teams successfully"), results));
    }
}
