package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.CompetitionRoundDTO;
import com.hacof.hackathon.service.CompetitionRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/competition-rounds")
@RequiredArgsConstructor
public class CompetitionRoundController {
    private final CompetitionRoundService roundService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CompetitionRoundDTO>>> getAllRounds() {
        List<CompetitionRoundDTO> rounds = roundService.getAllRounds();
        CommonResponse<List<CompetitionRoundDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all rounds successfully"),
                rounds);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roundId}")
    public ResponseEntity<CommonResponse<CompetitionRoundDTO>> getRoundById(@PathVariable Long roundId) {
        CompetitionRoundDTO round = roundService.getRoundById(roundId);
        CommonResponse<CompetitionRoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched round successfully"),
                round);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CompetitionRoundDTO>> createRound(
            @RequestBody CommonRequest<CompetitionRoundDTO> request) {
        CompetitionRoundDTO roundDTO = roundService.createRound(request.getData());
        CommonResponse<CompetitionRoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round created successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{roundId}")
    public ResponseEntity<CommonResponse<CompetitionRoundDTO>> updateRound(
            @PathVariable Long roundId, @RequestBody CommonRequest<CompetitionRoundDTO> request) {
        CompetitionRoundDTO roundDTO = roundService.updateRound(roundId, request.getData());
        CommonResponse<CompetitionRoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roundId}")
    public ResponseEntity<CommonResponse<Void>> deleteRound(@PathVariable Long roundId) {
        roundService.deleteRound(roundId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roundId}/assign-judge")
    public ResponseEntity<CommonResponse<Void>> assignJudgeToRound(
            @PathVariable Long roundId, @RequestBody Long judgeId) {
        roundService.assignJudgeToRound(roundId, judgeId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Judge assigned successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{teamId}/assign-task")
    public ResponseEntity<CommonResponse<Void>> assignTaskToMember(
            @PathVariable Long teamId, @RequestBody Map<String, Object> request) {
        Long memberId = Long.valueOf(request.get("memberId").toString());
        String task = request.get("task").toString();
        roundService.assignTaskToMember(teamId, memberId, task);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Task assigned successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
