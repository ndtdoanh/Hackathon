package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.ApproveRejectRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.service.TeamRequestService;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamRequestService teamRequestService;
    private final TeamService teamService;

    @PostMapping("/request")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> createTeamRequest(
            @RequestBody CommonRequest<TeamRequestDTO> request) {
        TeamRequestDTO teamRequestDTO = teamRequestService.createTeamRequest(request.getData());
        CommonResponse<TeamRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team Request created successfully"),
                teamRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request/confirm")
    public ResponseEntity<CommonResponse<Void>> confirmMemberRequest(
            @RequestBody CommonRequest<ApproveRejectRequestDTO> request) {
        teamRequestService.confirmMemberRequest(
                request.getData().getTeamRequestId(), request.getData().getUserId(), "CONFIRMED");
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Member request confirmed successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request/approve")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> approveTeamRequest(
            @RequestBody CommonRequest<ApproveRejectRequestDTO> request) {
        TeamRequestDTO teamRequestDTO = teamRequestService.approveTeamRequest(
                request.getData().getTeamRequestId(), request.getData().getUserId());
        CommonResponse<TeamRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team Request approved successfully by organizer/admin"),
                teamRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request/reject")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> rejectTeamRequest(
            @RequestBody CommonRequest<ApproveRejectRequestDTO> request) {
        TeamRequestDTO teamRequestDTO = teamRequestService.rejectTeamRequest(
                request.getData().getTeamRequestId(), request.getData().getUserId());
        CommonResponse<TeamRequestDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team Request rejected successfully by organizer/admin"),
                teamRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TeamDTO>> createTeam(@RequestBody CommonRequest<TeamDTO> request) {
        TeamDTO teamDTO = teamService.createTeam(request.getData());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team created successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamDTO>> updateTeam(
            @PathVariable long id, @RequestBody CommonRequest<TeamDTO> request) {
        TeamDTO teamDTO = teamService.updateTeam(id, request.getData());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team updated successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTeam(@PathVariable long id) {
        teamService.deleteTeam(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Team deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamDTO>> getTeamById(@PathVariable long id) {
        TeamDTO teamDTO = teamService.getTeamById(id);
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getAllTeams() {
        List<TeamDTO> teams = teamService.getAllTeams();
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all teams successfully"),
                teams);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getTeamsByHackathon(@PathVariable long hackathonId) {
        List<TeamDTO> teams = teamService.getTeamsByHackathon(hackathonId);
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched teams by hackathon successfully"),
                teams);
        return ResponseEntity.ok(response);
    }
}
// JPA Specification
