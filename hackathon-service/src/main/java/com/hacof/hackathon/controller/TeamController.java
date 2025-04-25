package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

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

import com.hacof.hackathon.dto.TeamBulkRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestMemberResponseDTO;
import com.hacof.hackathon.dto.TeamRequestReviewDTO;
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
    @PreAuthorize("hasAuthority('CREATE_TEAM_REQUEST')")
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

    @GetMapping("/requests/filter-by-member-and-hackathon")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getTeamRequestsByMemberIdAndHackathonId(
            @RequestParam("memberId") Long memberId, @RequestParam("hackathonId") Long hackathonId) {
        List<TeamRequestDTO> teamRequests =
                teamRequestService.getTeamRequestsByMemberIdAndHackathonId(memberId, hackathonId);
        CommonResponse<List<TeamRequestDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Success"),
                teamRequests);
        return ResponseEntity.ok(response);
    }

    // Step 2: Member responses to the team request
    @PostMapping("/requests/respond")
    @PreAuthorize("hasAuthority('RESPOND_TEAM_REQUEST')")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> respondToTeamRequest(
            @RequestBody CommonRequest<TeamRequestMemberResponseDTO> request) {
        TeamRequestDTO updated = teamRequestService.updateMemberResponse(
                request.getData().getRequestId(),
                request.getData().getUserId(),
                request.getData().getStatus(),
                request.getData().getNote());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team updated successfully"),
                updated));
    }

    // Step 3: Organizer/Admin review
    @PostMapping("/requests/review")
    @PreAuthorize("hasAuthority('REVIEW_TEAM_REQUEST')")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> reviewTeamRequest(
            @RequestBody CommonRequest<TeamRequestReviewDTO> request) {
        TeamRequestDTO reviewed = teamRequestService.reviewTeamRequest(
                request.getData().getRequestId(),
                request.getData().getStatus(),
                request.getData().getNote());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team request was reviewed successfully"),
                reviewed));
    }

    // Step 4: Search team requests
    @DeleteMapping("/requests/{id}")
    @PreAuthorize("hasAuthority('DELETE_TEAM_REQUEST')")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> deleteTeamRequest(@PathVariable Long id) {
        teamRequestService.deleteTeamRequest(id);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Delete team request successfully"),
                null));
    }

    @GetMapping("/requests")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getAllTeamRequests() {
        List<TeamRequestDTO> teamRequests = teamRequestService.getAllTeamRequests();
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all team requests successfully"),
                teamRequests));
    }

    @PostMapping("/requests/filter-by-hackathon-and-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getAllByHackathonIdAndUserId(
            @RequestBody Map<String, String> request) {
        log.debug("Request data: {}", request);

        String hackathonId = request.get("hackathonId");
        String userId = request.get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.getAllByHackathonIdAndUserId(hackathonId, userId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    @PostMapping("/requests/filter-by-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByUserId(
            @RequestBody Map<String, String> request) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(
                            UUID.randomUUID().toString(),
                            LocalDateTime.now(),
                            "HACOF",
                            new CommonResponse.Result("0600", "Invalid request data"),
                            null));
        }
        String userId = request.get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.filterByUserId(userId);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    @GetMapping("/requests/filter-by-hackathon")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByHackathonId(
            @RequestParam("hackathonId") String hackathonId) {

        List<TeamRequestDTO> teamRequests = teamRequestService.filterByHackathonId(hackathonId);

        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    // --- TEAM ENDPOINTS ---
    // Step 1: Create bulk team
    // pending
    @PostMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> createBulkTeams(@RequestBody Map<String, Object> request) {
        Map<String, Object> data = request;
        String teamLeaderId = (String) data.get("teamLeaderId");
        List<Long> userIds = ((List<String>) data.get("teamMembers"))
                .stream().map(Long::parseLong).collect(Collectors.toList());
        List<TeamDTO> createdTeams = teamService.createBulkTeams(teamLeaderId, userIds);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Bulk teams created successfully"),
                createdTeams));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAuthority('CREATE_BULK_TEAM')")
    public ResponseEntity<CommonResponse<List<TeamDTO>>> createBulkTeams(
            @Valid @RequestBody CommonRequest<List<TeamBulkRequestDTO>> bulkRequest) {
        List<TeamDTO> createdTeams = teamService.createBulkTeams(bulkRequest.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Bulk teams created successfully"),
                createdTeams));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_BULK_TEAM')")
    public ResponseEntity<CommonResponse<TeamDTO>> updateTeam(@RequestBody CommonRequest<TeamDTO> request) {
        String id = request.getData().getId();
        TeamDTO teamDTO = teamService.updateTeam(Long.parseLong(id), request.getData());
        CommonResponse<TeamDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Team updated successfully"),
                teamDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TEAM')")
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

    @GetMapping("/by-hackathon/{hackathonId}")
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getTeamsByHackathonId(@PathVariable Long hackathonId) {
        List<TeamDTO> teams = teamService.getTeamsByHackathonId(hackathonId);
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched teams successfully"),
                teams);
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

    @GetMapping("/by-user-and-hackathon")
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getTeamsByUserIdAndHackathonId(
            @RequestParam Long userId, @RequestParam Long hackathonId) {
        List<TeamDTO> teams = teamService.getTeamsByUserIdAndHackathonId(userId, hackathonId);
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Teams fetched successfully"),
                teams);
        return ResponseEntity.ok(response);
    }
}
