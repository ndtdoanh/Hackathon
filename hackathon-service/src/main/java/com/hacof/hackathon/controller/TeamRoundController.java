package com.hacof.hackathon.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.TeamRoundStatus;
import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.dto.TeamRoundSearchDTO;
import com.hacof.hackathon.service.TeamRoundService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/team-rounds")
@RequiredArgsConstructor
@Slf4j
public class TeamRoundController {
    private final TeamRoundService teamRoundService;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamRoundDTO>> createTeamRound(
            @Valid @RequestBody CommonRequest<TeamRoundDTO> request) {
        log.debug("Tạo team round mới: {}", request.getData());
        TeamRoundDTO created = teamRoundService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Team Round was created"), created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamRoundDTO>> updateTeamRound(
            @PathVariable String id, @Valid @RequestBody CommonRequest<TeamRoundDTO> request) {

        TeamRoundDTO updated = teamRoundService.update(id, request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Update TeamRound Successfully"), updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTeamRound(@PathVariable String id) {

        teamRoundService.delete(id);
        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Delete TeamRound successfully"), null));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<TeamRoundDTO>>> searchTeamRounds(
            @RequestParam(required = false) String teamId,
            @RequestParam(required = false) String roundId,
            @RequestParam(required = false) TeamRoundStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        TeamRoundSearchDTO searchDTO = TeamRoundSearchDTO.builder()
                .teamId(teamId)
                .roundId(roundId)
                .status(status)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        Page<TeamRoundDTO> result = teamRoundService.searchTeamRounds(searchDTO);

        return ResponseEntity.ok(new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Tìm kiếm thành công"), result));
    }

    @PostMapping("/filter-by-round")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> getAllByRoundId(
            @RequestBody Map<String, String> request) {
        String roundId = request.get("roundId");
        List<TeamRoundDTO> teamRounds = teamRoundService.getAllByRoundId(roundId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Fetched team rounds successfully"), teamRounds));
    }

    @PostMapping("/filter-by-judge-and-round")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> getAllByJudgeIdAndRoundId(
            @RequestBody Map<String, String> request) {
        String judgeId = request.get("judgeId");
        String roundId = request.get("roundId");

        if (judgeId == null || roundId == null) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponse<>(
                            new CommonResponse.Result("0400", "Invalid request: judgeId or roundId is missing"), null));
        }

        List<TeamRoundDTO> teamRounds = teamRoundService.getAllByJudgeIdAndRoundId(judgeId, roundId);
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Fetched team rounds successfully"), teamRounds));
    }

    @PutMapping("/bulk")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> updateBulkTeamRounds(
            @Valid @RequestBody List<TeamRoundDTO> teamRoundDTOList) {
        List<TeamRoundDTO> updatedTeamRounds = teamRoundService.updateBulk(teamRoundDTOList);
        return ResponseEntity.ok(
                new CommonResponse<>(new CommonResponse.Result("0000", "Bulk update successful"), updatedTeamRounds));
    }

    @GetMapping("/filter-by-round-and-team")
    public ResponseEntity<CommonResponse<List<TeamRoundDTO>>> getAllByJudgeIdAndRoundId(
            @RequestParam String roundId, @RequestParam String judgeId) {
        List<TeamRoundDTO> teamRounds = teamRoundService.getAllByJudgeIdAndRoundId(roundId, judgeId);
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Fetched team rounds successfully"), teamRounds));
    }
}
