package com.hacof.submission.controller;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.TeamRoundJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teamroundjudges")
public class TeamRoundJudgeController {

    @Autowired
    private TeamRoundJudgeService service;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> create(
            @RequestBody TeamRoundJudgeRequestDTO teamRoundJudgeRequestDTO) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO created = service.createTeamRoundJudge(teamRoundJudgeRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Team round judge created successfully!");
            response.setData(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> update(
            @PathVariable Long id, @RequestBody TeamRoundJudgeRequestDTO updatedTeamRoundJudge) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO updated = service.updateTeamRoundJudge(id, updatedTeamRoundJudge);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge updated successfully!");
            response.setData(updated);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
