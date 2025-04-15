package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.service.TeamRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/team-rounds")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamRoundController {
    TeamRoundService teamRoundService;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamRoundDTO>> createTeamRound(
            @Valid @RequestBody CommonRequest<TeamRoundDTO> request) {
        TeamRoundDTO created = teamRoundService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Team Round create successfully!"),
                created));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TeamRoundDTO>> updateTeamRound(@Valid @RequestBody TeamRoundDTO request) {
        String id = request.getId();
        TeamRoundDTO updated = teamRoundService.update(id, request);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Update successfully"),
                updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTeamRound(@PathVariable String id) {
        log.debug("Xóa team round: {}", id);
        teamRoundService.delete(id);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Xóa thành công team round"),
                null));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> getAllByRoundId(@RequestParam("roundId") String roundId) {
        List<TeamRoundDTO> teamRounds = teamRoundService.getAllByRoundId(roundId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team rounds successfully"),
                teamRounds));
    }

    @PostMapping("/filter-by-judge-and-round")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> getAllByJudgeIdAndRoundId(
            @RequestBody Map<String, String> request) {
        String judgeId = request.get("judgeId");
        String roundId = request.get("roundId");

        if (judgeId == null || roundId == null) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponse<>(
                            UUID.randomUUID().toString(),
                            LocalDateTime.now(),
                            "HACOF",
                            new CommonResponse.Result("0400", "Invalid request: judgeId or roundId is missing"),
                            null));
        }

        List<TeamRoundDTO> teamRounds = teamRoundService.getAllByJudgeIdAndRoundId(judgeId, roundId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team rounds successfully"),
                teamRounds));
    }

    @PutMapping("/bulk")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> updateBulkTeamRounds(
            @Valid @RequestBody List<TeamRoundDTO> teamRoundDTOList) {
        List<TeamRoundDTO> updatedTeamRounds = teamRoundService.updateBulk(teamRoundDTOList);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Bulk update successful"),
                updatedTeamRounds));
    }
}
