package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.InviteRequest;
import com.hacof.hackathon.dto.JoinRequest;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<CommonResponse<TeamDTO>> createTeam(@RequestBody CommonRequest<TeamDTO> request) {
        log.debug("Received request to create team: {}", request);
        TeamDTO createdTeam = teamService.createTeam(
                request.getData(), request.getData().getMemberIds().get(0));
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team created successfully"),
                createdTeam);
        log.debug("Created team: {}", createdTeam);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/invite")
    public ResponseEntity<CommonResponse<TeamDTO>> inviteMemberToTeam(
            @RequestBody CommonRequest<InviteRequest> request) {
        log.debug(
                "Received request to invite member {} to team {}",
                request.getData().getUserId(),
                request.getData().getTeamId());
        TeamDTO updatedTeam = teamService.inviteMemberToTeam(
                request.getData().getTeamId(), request.getData().getUserId());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Member invited to team successfully"),
                updatedTeam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request")
    public ResponseEntity<CommonResponse<TeamDTO>> requestToJoinTeam(@RequestBody CommonRequest<JoinRequest> request) {
        log.debug(
                "Received request from user {} to join team {}",
                request.getData().getUserId(),
                request.getData().getTeamId());
        TeamDTO updatedTeam = teamService.requestToJoinTeam(
                request.getData().getTeamId(), request.getData().getUserId());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Request to join team successful"),
                updatedTeam);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getAllTeams() {
        List<TeamDTO> teams = teamService.getAllTeams();
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Teams retrieved successfully"),
                teams);
        return ResponseEntity.ok(response);
    }
}
