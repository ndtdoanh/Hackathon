package com.hacof.submission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.TeamRoundJudgeService;

@RestController
@RequestMapping("/api/v1/teamroundjudges")
public class TeamRoundJudgeController {

    @Autowired
    private TeamRoundJudgeService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> getAll() {
        CommonResponse<List<TeamRoundJudgeResponseDTO>> response = new CommonResponse<>();
        try {
            List<TeamRoundJudgeResponseDTO> data = service.getAllTeamRoundJudges();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all team round judges successfully!");
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> getById(@PathVariable Long id) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO teamRoundJudge = service.getTeamRoundJudgeById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched team round judge by ID successfully!");
            response.setData(teamRoundJudge);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.deleteTeamRoundJudge(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge deleted successfully!");
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

    @GetMapping("/by-team-round/{teamRoundId}")
    public ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> getByTeamRoundId(
            @PathVariable Long teamRoundId) {
        CommonResponse<List<TeamRoundJudgeResponseDTO>> response = new CommonResponse<>();
        try {
            List<TeamRoundJudgeResponseDTO> data = service.getTeamRoundJudgesByTeamRoundId(teamRoundId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched team round judges successfully!");
            response.setData(data);
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

    @DeleteMapping("/by-team-round-judge")
    public ResponseEntity<CommonResponse<Void>> deleteByTeamRoundAndJudge(
            @RequestParam Long teamRoundId,
            @RequestParam Long judgeId) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(teamRoundId, judgeId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge deleted successfully!");
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
