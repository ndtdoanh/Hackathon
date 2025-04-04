package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.dto.*;
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

    // --- TEAM REQUEST ENDPOINTS ---
    // Step 1: Leader creates a team request
    @PostMapping("/requests")
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
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Đã cập nhật phản hồi thành công"),
                updated));
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
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Đã xét duyệt yêu cầu thành công"),
                reviewed));
    }

    // Step 4: Search team requests
    @GetMapping("/requests")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> searchTeamRequests(
            @RequestParam(required = false) String hackathonId,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) TeamRequestStatus status,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        TeamRequestSearchDTO searchDTO = TeamRequestSearchDTO.builder()
                .hackathonId(hackathonId)
                .teamName(teamName)
                .status(status)
                .memberId(memberId)
                .fromDate(fromDate)
                .toDate(toDate)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        List<TeamRequestDTO> result = teamRequestService.searchTeamRequests(searchDTO);

        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Tìm kiếm thành công"),
                result
        ));
    }

    @PostMapping("/requests/filter-by-hackathon-and-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> getAllByHackathonIdAndUserId(
            @RequestBody CommonRequest<Map<String, String>> request) {
        String hackathonId = request.getData().get("hackathonId");
        String userId = request.getData().get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.getAllByHackathonIdAndUserId(hackathonId, userId);
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    @PostMapping("/requests/filter-by-user")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByUserId(
            @RequestBody CommonRequest<Map<String, String>> request) {
        String userId = request.getData().get("userId");
        List<TeamRequestDTO> teamRequests = teamRequestService.filterByUserId(userId);
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    @PostMapping("/requests/filter-by-hackathon")
    public ResponseEntity<CommonResponse<List<TeamRequestDTO>>> filterByHackathonId(
            @RequestBody CommonRequest<Map<String, String>> request) {
        String hackathonId = request.getData().get("hackathonId");
        List<TeamRequestDTO> teamRequests = teamRequestService.filterByHackathonId(hackathonId);
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Fetched team requests successfully"),
                teamRequests));
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTeamRequest(@PathVariable Long id) {
        teamRequestService.deleteTeamRequest(id);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Team request deleted successfully"),
                null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //    @PostMapping("/request/reject")
    //    public ResponseEntity<CommonResponse<TeamRequestDTO>> rejectTeamRequest(
    //            @RequestBody CommonRequest<ApproveRejectRequestDTO> request) {
    //        TeamRequestDTO teamRequestDTO = teamRequestService.rejectTeamRequest(
    //                request.getData().getTeamRequestId(), request.getData().getUserId());
    //        CommonResponse<TeamRequestDTO> response = new CommonResponse<>(
    //                request.getRequestId(),
    //                LocalDateTime.now(),
    //                request.getChannel(),
    //                new CommonResponse.Result("0000", "Team Request rejected successfully by organizer/admin"),
    //                teamRequestDTO);
    //        return ResponseEntity.ok(response);
    //    }

    // --- TEAM ENDPOINTS ---
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

    @PostMapping("/create-with-participants")
    public ResponseEntity<CommonResponse<TeamDTO>> createTeamWithParticipants(
            @RequestBody CommonRequest<CreateTeamRequestDTO> request) {
        TeamDTO teamDTO = teamService.createTeamWithParticipants(
                request.getData().getTeamName(), request.getData().getRequestIds());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Team created successfully"),
                teamDTO));
    }
}
// JPA Specification
