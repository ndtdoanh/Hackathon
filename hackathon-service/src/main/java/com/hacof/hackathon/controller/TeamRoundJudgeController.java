package com.hacof.hackathon.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.TeamRoundJudgeDTO;
import com.hacof.hackathon.service.TeamRoundJudgeService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/team-round-judges")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class TeamRoundJudgeController {
    TeamRoundJudgeService teamRoundJudgeService;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamRoundJudgeDTO>> createTeamRound(
            @RequestBody CommonRequest<TeamRoundJudgeDTO> request) {
        TeamRoundJudgeDTO created = teamRoundJudgeService.create(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Team Round Judge was created"), created));
    }

    /*
     * @PostMapping
     * Theses below method are pending to be implemented, because it belons to Submission-service
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeamRoundJudgeDTO> update(@PathVariable Long id, @RequestBody @Valid TeamRoundJudgeDTO dto) {
        return ResponseEntity.ok(teamRoundJudgeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamRoundJudgeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TeamRoundJudgeDTO>> getAll() {
        return ResponseEntity.ok(teamRoundJudgeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamRoundJudgeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teamRoundJudgeService.getById(id));
    }
}
