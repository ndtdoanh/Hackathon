package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.hacof.hackathon.constant.StatusCode;
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
    @PreAuthorize("hasAuthority('GET_ROUNDS')")
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
    @PreAuthorize("hasAuthority('GET_ROUND')")
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
    @PreAuthorize("hasAuthority('CREATE_ROUND')")
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

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_ROUND')")
    public ResponseEntity<CommonResponse<CompetitionRoundDTO>> updateRound(
            @RequestParam Long roundId, @RequestBody CommonRequest<CompetitionRoundDTO> request) {
        CompetitionRoundDTO roundDTO = roundService.updateRound(roundId, request.getData());
        CommonResponse<CompetitionRoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_ROUND')")
    public ResponseEntity<CommonResponse<Void>> deleteRound(@RequestParam Long roundId) {
        roundService.deleteRound(roundId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
    // assign judge
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ASSIGN_JUDGES_AND_MENTORS')")
    public ResponseEntity<CommonResponse<CompetitionRoundDTO>> assignJudgesAndMentors(
            @Valid @RequestBody CompetitionRoundDTO request) {
        CompetitionRoundDTO roundDTO =
                roundService.assignJudgesAndMentors(request.getId(), request.getJudgeIds(), request.getMentorIds());
        CommonResponse<CompetitionRoundDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Judges and mentors assigned successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{teamId}/assign-task")
    @PreAuthorize("hasAuthority('ASSIGN_TASK_TO_MEMBER')")
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

    @GetMapping("/{roundId}/passed-teams")
    @PreAuthorize("hasAuthority('GET_PASSED_TEAMS')")
    public ResponseEntity<CommonResponse<List<String>>> getPassedTeams(@PathVariable Long roundId) {
        List<String> passedTeams = roundService.getPassedTeams(roundId);
        CommonResponse<List<String>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched passed teams successfully"),
                passedTeams);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roundId}/judges")
    @PreAuthorize("hasAuthority('GET_JUDGE_NAMES')")
    public ResponseEntity<CommonResponse<List<String>>> getJudgeNames(@PathVariable Long roundId) {
        List<String> judgeNames = roundService.getJudgeNames(roundId);
        CommonResponse<List<String>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched judge names successfully"),
                judgeNames);
        return ResponseEntity.ok(response);
    }
}
