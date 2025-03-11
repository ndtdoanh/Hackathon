package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_TEAM')")
    public ResponseEntity<CommonResponse<TeamDTO>> createTeam(@RequestBody CommonRequest<TeamDTO> request) {
        Long userId = request.getData().getLeaderId();
        TeamDTO teamDTO = teamService.createTeam(request.getData(), userId);
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team created successfully"),
                teamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/add-member")
    @PreAuthorize("hasAuthority('ADD_MEMBER_TO_TEAM')")
    public ResponseEntity<CommonResponse<TeamDTO>> addMemberToTeam(
            @RequestBody CommonRequest<Map<String, Long>> request) {
        Long teamId = request.getData().get("teamId");
        Long memberId = request.getData().get("memberId");
        TeamDTO teamDTO = teamService.addMemberToTeam(teamId, memberId);
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Member added successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assign-mentor")
    @PreAuthorize("hasAuthority('ASSIGN_MENTOR_TO_TEAM')")
    public ResponseEntity<CommonResponse<TeamDTO>> assignMentorToTeam(
            @RequestBody CommonRequest<Map<String, Long>> request) {
        Long teamId = request.getData().get("teamId");
        Long mentorId = request.getData().get("mentorId");
        TeamDTO teamDTO = teamService.assignMentorToTeam(teamId, mentorId);
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Mentor assigned successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_TEAM')")
    public ResponseEntity<CommonResponse<TeamDTO>> updateTeam(@RequestBody CommonRequest<TeamDTO> request) {
        Long teamId = request.getData().getId();
        TeamDTO teamDTO = teamService.updateTeam(teamId, request.getData());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team updated successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-member")
    @PreAuthorize("hasAuthority('REMOVE_MEMBER_FROM_TEAM')")
    public ResponseEntity<CommonResponse<Void>> removeMemberFromTeam(
            @RequestBody CommonRequest<Map<String, Long>> request) {
        Long teamId = request.getData().get("teamId");
        Long memberId = request.getData().get("memberId");
        teamService.removeMemberFromTeam(teamId, memberId);
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Member removed successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE_TEAM')")
    public ResponseEntity<CommonResponse<Void>> deleteTeam(@RequestBody CommonRequest<Map<String, Long>> request) {
        Long teamId = request.getData().get("teamId");
        teamService.deleteTeam(teamId);
        CommonResponse<Void> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_TEAMS')")
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
}
