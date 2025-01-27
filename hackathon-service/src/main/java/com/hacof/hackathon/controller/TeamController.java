package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{teamId}/invite")
    public ResponseEntity<CommonResponse<Void>> inviteMember(
            @PathVariable Long teamId, @RequestBody String memberEmail) {
        teamService.inviteMember(teamId, memberEmail);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Member invited successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{teamId}/assign-mentor")
    public ResponseEntity<CommonResponse<Void>> assignMentor(@PathVariable Long teamId, @RequestBody Long mentorId) {
        teamService.assignMentor(teamId, mentorId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Mentor assigned successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
