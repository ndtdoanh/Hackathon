package com.hacof.hackathon.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.*;
import com.hacof.hackathon.service.TeamRequestService;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class TeamController {
    TeamRequestService teamRequestService;
    TeamService teamService;

    // --- TEAM REQUEST ENDPOINTS ---
    // Step 1: Leader creates a team request
    @PostMapping("/requests")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> createTeamRequest(
            @RequestBody CommonRequest<TeamRequestDTO> request) {
        TeamRequestDTO teamRequestDTO = teamRequestService.createTeamRequest(request.getData());
        CommonResponse<TeamRequestDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Team Request created successfully"), teamRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Step 2: Member responses to the team request
    @PostMapping("/requests/respond")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> respondToTeamRequest(
            @RequestBody CommonRequest<TeamRequestMemberResponseDTO> request) {
        TeamRequestDTO updated = teamRequestService.updateMemberResponse(
                request.getData().getRequestId(),
                request.getData().getUserId(),
                request.getData().getStatus(),
                request.getData().getNote());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Đã cập nhật phản hồi thành công"), updated));
    }

    // Step 3: Organizer/Admin review
    @PostMapping("/requests/review")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> reviewTeamRequest(
            @RequestBody CommonRequest<TeamRequestReviewDTO> request) {
        TeamRequestDTO reviewed = teamRequestService.reviewTeamRequest(
                request.getData().getRequestId(),
                request.getData().getStatus(),
                request.getData().getNote());
        return ResponseEntity.ok(new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Đã xét duyệt yêu cầu thành công"), reviewed));
    }

    // Step 4: Search team requests
    @GetMapping("/requests")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getAllTeamRequests() {
        List<TeamRequestDTO> teamRequests = teamRequestService.getAllTeamRequests();
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Fetched all team requests successfully"), teamRequests));
    }

    @PostMapping("/requests/filter-by-hackathon-and-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getAllByHackathonIdAndUserId(
            @RequestBody Map<String, String> request) {
        log.debug("Request data: {}", request);
        if (request == null || request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(new CommonResponse.Result("0600", "Invalid request data"), null));
        }
        String hackathonId = request.get("hackathonId");
        String userId = request.get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.getAllByHackathonIdAndUserId(hackathonId, userId);
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Fetched team requests successfully"), teamRequests));
    }

    @PostMapping("/requests/filter-by-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByUserId(
            @RequestBody Map<String, String> request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(new CommonResponse.Result("0600", "Invalid request data"), null));
        }
        String userId = request.get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.filterByUserId(userId);
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Fetched team requests successfully"), teamRequests));
    }

    @PostMapping("/requests/filter-by-hackathon")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByHackathonId(
            @RequestBody Map<String, String> request) {

        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(new CommonResponse.Result("0600", "Invalid request data"), null));
        }
        String hackathonId = request.get("hackathonId");
        List<TeamRequestDTO> teamRequests = teamRequestService.filterByHackathonId(hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Fetched team requests successfully"), teamRequests));
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> deleteTeamRequest(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(new CommonResponse.Result("0600", "Invalid request data"), null));
        }

        teamRequestService.deleteTeamRequest(id);

        return ResponseEntity.ok(
                new CommonResponse<>(new CommonResponse.Result("0000", "Delete team request successfully"), null));
    }

    // --- TEAM ENDPOINTS ---
    // Step 1: Create bulk team
    @PostMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> createBulkTeams(@RequestBody Map<String, Object> request) {
        Map<String, Object> data = request;
        String teamLeaderId = (String) data.get("teamLeaderId");
        List<Long> userIds = ((List<String>) data.get("teamMembers"))
                .stream().map(Long::parseLong).collect(Collectors.toList());
        List<TeamDTO> createdTeams = teamService.createBulkTeams(teamLeaderId, userIds);
        return ResponseEntity.ok(new CommonResponse<>(
                new CommonResponse.Result("0000", "Bulk teams created successfully"), createdTeams));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamDTO>> updateTeam(
            @PathVariable long id, @RequestBody CommonRequest<TeamDTO> request) {
        TeamDTO teamDTO = teamService.updateTeam(id, request.getData());
        CommonResponse<TeamDTO> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Team updated successfully"), teamDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTeam(@PathVariable long id) {
        teamService.deleteTeam(id);
        CommonResponse<Void> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Team deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamDTO>> getTeamById(@PathVariable long id) {
        TeamDTO teamDTO = teamService.getTeamById(id);
        CommonResponse<TeamDTO> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Fetched team successfully"), teamDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getAllTeams() {
        List<TeamDTO> teams = teamService.getAllTeams();
        CommonResponse<List<TeamDTO>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Fetched all teams successfully"), teams);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-user-and-hackathon")
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getTeamsByUserIdAndHackathonId(
            @RequestParam Long userId, @RequestParam Long hackathonId) {
        List<TeamDTO> teams = teamService.getTeamsByUserIdAndHackathonId(userId, hackathonId);
        CommonResponse<List<TeamDTO>> response =
                new CommonResponse<>(new CommonResponse.Result("0000", "Teams fetched successfully"), teams);
        return ResponseEntity.ok(response);
    }
}
