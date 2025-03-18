package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.*;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.specification.TeamSpecification;
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
    /**
     *
     * <h3> Step 1: Create team request </h3>
     * <h3> Step 2: Approve or reject team request - Admin Or Organizer </h3>
     * <h2> Step 2.1: Approve team request, if not - change to step 2.2 </h2>
     * <h2> Step 2.2: Reject team request </h2>
     * <h3> Step 3: Team leader invite member or member request to join team </h3>
     * <h2> Step 3.1: Team leader invite member </h2>
     * <h1> Step 3.1.1: Member accept invitation, if not - change to step 3.2 </h1>
     * <h1> Step 3.1.2: Member reject invitation </h1>
     * <h2> Step 3.2: Member request to join team </h2>
     * <h1> Step 3.2.1: Team leader approve join request, if not - change to step 3.2.2 </h1>
     * <h1> Step 3.2.2: Team leader reject join request </h1>
     * <h3> Step 4: Team leader register team for hackathon </h3>
     * <h3> Step 5: Team leader update team information, get information of team </h3>
     * <h3> Step 6: Team leader remove member from team if nescessary </h3>
     *
     */
    @PostMapping("/requests")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> createTeamRequest(
            @Valid @RequestBody CommonRequest<TeamRequestDTO> request) {
        log.debug("Received request to create team request: {}", request);
        TeamRequestDTO result = teamService.createTeamRequest(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Team request created successfully"),
                result));
    }

    @PostMapping("/requests/approve")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> approveTeamRequest(
            @Valid @RequestBody CommonRequest<ApprovalRequestDTO> request) {
        log.debug("Received request to approve team request: {}", request);
        TeamRequestDTO result = teamService.approveTeamRequest(
                request.getData().getRequestId(), request.getData().getReviewerId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Team request approved successfully"),
                result));
    }

    @PostMapping("/requests/reject")
    public ResponseEntity<CommonResponse<TeamRequestDTO>> rejectTeamRequest(
            @Valid @RequestBody CommonRequest<ApprovalRequestDTO> request) {
        log.debug("Received request to reject team request: {}", request);
        TeamRequestDTO result = teamService.rejectTeamRequest(
                request.getData().getRequestId(), request.getData().getReviewerId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Team request rejected successfully"),
                result));
    }

    @PostMapping("/members/invite")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> inviteMember(
            @Valid @RequestBody CommonRequest<InviteRequestDTO> request) {
        log.debug("Received request to invite member: {}", request);
        UserTeamRequestDTO result = teamService.inviteMember(
                request.getData().getTeamId(),
                request.getData().getUserId(),
                request.getData().getInviterId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Member invited successfully"),
                result));
    }

    @PostMapping("/invitations/accept")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> acceptInvitation(
            @Valid @RequestBody CommonRequest<UserActionRequestDTO> request) {
        log.debug("Received request to accept invitation: {}", request);
        UserTeamRequestDTO result = teamService.acceptInvitation(
                request.getData().getRequestId(), request.getData().getUserId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Invitation accepted successfully"),
                result));
    }

    @PostMapping("/invitations/reject")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> rejectInvitation(
            @Valid @RequestBody CommonRequest<UserActionRequestDTO> request) {
        log.debug("Received request to reject invitation: {}", request);
        UserTeamRequestDTO result = teamService.rejectInvitation(
                request.getData().getRequestId(), request.getData().getUserId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Invitation rejected successfully"),
                result));
    }

    @PostMapping("/members/join")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> requestToJoinTeam(
            @Valid @RequestBody CommonRequest<JoinTeamRequestDTO> request) {
        log.debug("Received request to join team: {}", request);
        UserTeamRequestDTO result = teamService.requestToJoinTeam(
                request.getData().getTeamId(), request.getData().getUserId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Join request sent successfully"),
                result));
    }

    @PostMapping("/members/approve")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> approveJoinRequest(
            @Valid @RequestBody CommonRequest<ApprovalRequestDTO> request) {
        log.debug("Received request to approve join request: {}", request);
        UserTeamRequestDTO result = teamService.approveJoinRequest(
                request.getData().getRequestId(), request.getData().getLeaderId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Join request approved successfully"),
                result));
    }

    @PostMapping("/members/reject")
    public ResponseEntity<CommonResponse<UserTeamRequestDTO>> rejectJoinRequest(
            @Valid @RequestBody CommonRequest<ApprovalRequestDTO> request) {
        log.debug("Received request to reject join request: {}", request);
        UserTeamRequestDTO result = teamService.rejectJoinRequest(
                request.getData().getRequestId(), request.getData().getLeaderId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Join request rejected successfully"),
                result));
    }

    @PostMapping("/hackathons")
    public ResponseEntity<CommonResponse<TeamDTO>> registerForHackathon(
            @Valid @RequestBody CommonRequest<TeamDTO> request) {
        log.debug("Received request to register team for hackathon: {}", request);
        TeamDTO result = teamService.registerForHackathon(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Team registered successfully"),
                result));
    }

    @PostMapping("/hackathons/remove")
    public ResponseEntity<CommonResponse<Void>> removeMember(@Valid @RequestBody CommonRequest<UserTeamDTO> request) {
        log.debug("Received request to remove member: {}", request);
        teamService.removeMember(request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Member removed successfully"),
                null));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamDTO>>> getTeams(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        Specification<Team> spec =
                Specification.where(TeamSpecification.hasId(id)).and(TeamSpecification.hasName(name));

        List<TeamDTO> teams = teamService.getTeams(spec);
        CommonResponse<List<TeamDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched teams successfully"),
                teams);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamDTO>> getTeam(@PathVariable long id) {
        TeamDTO teamDTO = teamService.getTeamById(id);
        return ResponseEntity.ok(new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched team successfully"),
                teamDTO));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TeamDTO>> updateTeam(@Valid @RequestBody CommonRequest<TeamDTO> request) {
        log.debug("Received request to update team: {}", request);
        long id = request.getData().getId();
        TeamDTO teamDTO = request.getData();
        teamDTO.setId(id);
        TeamDTO result = teamService.updateTeam(teamDTO);
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Team updated successfully"),
                result));
    }
}
