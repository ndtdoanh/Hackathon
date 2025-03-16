package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.specification.RoundSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rounds")
@RequiredArgsConstructor
public class RoundController {
    private final RoundService roundService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundDTO>>> getAllRounds(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer maxTeam,
            @RequestParam(required = false) Boolean isVideoRound) {
        Specification<Round> spec = Specification.where(null);
        spec = spec.and(id != null ? RoundSpecification.hasId(id) : null);
        spec = spec.and(name != null ? RoundSpecification.hasName(name) : null);
        spec = spec.and(description != null ? RoundSpecification.hasDescription(description) : null);
        spec = spec.and(startDate != null ? RoundSpecification.hasStartDate(startDate) : null);
        spec = spec.and(endDate != null ? RoundSpecification.hasEndDate(endDate) : null);
        spec = spec.and(maxTeam != null ? RoundSpecification.hasMaxTeam(maxTeam) : null);
        spec = spec.and(isVideoRound != null ? RoundSpecification.hasIsVideoRound(isVideoRound) : null);

        List<RoundDTO> rounds = roundService.getRoundByAllCriteria(spec);
        CommonResponse<List<RoundDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all rounds successfully"),
                rounds);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('CREATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> createRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.createRound(request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round created successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    // @PreAuthorize("hasAuthority('UPDATE_ROUND')")
    public ResponseEntity<CommonResponse<RoundDTO>> updateRound(@Valid @RequestBody CommonRequest<RoundDTO> request) {
        RoundDTO roundDTO = roundService.updateRound(request.getData().getId(), request.getData());
        CommonResponse<RoundDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round updated successfully"),
                roundDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    // @PreAuthorize("hasAuthority('DELETE_ROUND')")
    public ResponseEntity<CommonResponse<Void>> deleteRound(@RequestBody CommonRequest<RoundDTO> request) {
        roundService.deleteRound(request.getData().getId());
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Round deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
    //    // assign judge
    //    @PostMapping("/assign")
    //    // @PreAuthorize("hasAuthority('ASSIGN_JUDGES_AND_MENTORS')")
    //    public ResponseEntity<CommonResponse<RoundDTO>> assignJudgesAndMentors(@Valid @RequestBody RoundDTO request) {
    //        RoundDTO roundDTO =
    //                roundService.assignJudgesAndMentors(request.getId(), request.getJudgeIds(),
    // request.getMentorIds());
    //        CommonResponse<RoundDTO> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Judges and mentors assigned successfully"),
    //                roundDTO);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @PostMapping("/{teamId}/assign-task")
    //    // @PreAuthorize("hasAuthority('ASSIGN_TASK_TO_MEMBER')")
    //    public ResponseEntity<CommonResponse<Void>> assignTaskToMember(
    //            @PathVariable Long teamId, @RequestBody Map<String, Object> request) {
    //        Long memberId = Long.valueOf(request.get("memberId").toString());
    //        String task = request.get("task").toString();
    //        roundService.assignTaskToMember(teamId, memberId, task);
    //        CommonResponse<Void> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Task assigned successfully"),
    //                null);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping("/{roundId}/passed-teams")
    //    // @PreAuthorize("hasAuthority('GET_PASSED_TEAMS')")
    //    public ResponseEntity<CommonResponse<List<String>>> getPassedTeams(@PathVariable Long roundId) {
    //        List<String> passedTeams = roundService.getPassedTeams(roundId);
    //        CommonResponse<List<String>> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched passed teams successfully"),
    //                passedTeams);
    //        return ResponseEntity.ok(response);
    //    }
    //
    //    @GetMapping("/{roundId}/judges")
    //    // @PreAuthorize("hasAuthority('GET_JUDGE_NAMES')")
    //    public ResponseEntity<CommonResponse<List<String>>> getJudgeNames(@PathVariable Long roundId) {
    //        List<String> judgeNames = roundService.getJudgeNames(roundId);
    //        CommonResponse<List<String>> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result("0000", "Fetched judge names successfully"),
    //                judgeNames);
    //        return ResponseEntity.ok(response);
    //    }
}
