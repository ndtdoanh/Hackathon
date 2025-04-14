package com.hacof.submission.controller;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.service.TeamRoundJudgeService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teamroundjudges")
public class TeamRoundJudgeController {

    @Autowired
    private TeamRoundJudgeService service;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(
                request.getRequestId() != null
                        ? request.getRequestId()
                        : UUID.randomUUID().toString());
        response.setRequestDateTime(
                request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamRoundJudgeResponseDTO>>> getAll() {
        CommonResponse<List<TeamRoundJudgeResponseDTO>> response = new CommonResponse<>();
        try {
            List<TeamRoundJudgeResponseDTO> data = service.getAllTeamRoundJudges();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all team round judges successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> getById(@PathVariable Long id) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO data = service.getTeamRoundJudgeById(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched team round judge by ID successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TEAM_ROUND_JUDGE')")
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> create(
            @RequestBody CommonRequest<TeamRoundJudgeRequestDTO> request) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO created = service.createTeamRoundJudge(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Team round judge created successfully!");
            response.setData(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_TEAM_ROUND_JUDGE')")
    public ResponseEntity<CommonResponse<TeamRoundJudgeResponseDTO>> update(
            @PathVariable Long id, @RequestBody CommonRequest<TeamRoundJudgeRequestDTO> request) {
        CommonResponse<TeamRoundJudgeResponseDTO> response = new CommonResponse<>();
        try {
            TeamRoundJudgeResponseDTO updated = service.updateTeamRoundJudge(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge updated successfully!");
            response.setData(updated);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TEAM_ROUND_JUDGE')")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.deleteTeamRoundJudge(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched team round judges successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/by-team-round-judge")
    @PreAuthorize("hasAuthority('DELETE_BY_TEAM_ROUND_AND_JUDGE')")
    public ResponseEntity<CommonResponse<Void>> deleteByTeamRoundAndJudge(
            @RequestParam Long teamRoundId, @RequestParam Long judgeId) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(teamRoundId, judgeId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Team round judge deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
