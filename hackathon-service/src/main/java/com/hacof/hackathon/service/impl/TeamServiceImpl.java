package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.UserTeamDTO;
import com.hacof.hackathon.dto.UserTeamRequestDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.mapper.TeamRequestMapper;
import com.hacof.hackathon.mapper.UserTeamRequestMapper;
import com.hacof.hackathon.repository.*;
import com.hacof.hackathon.service.TeamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final TeamMapper teamMapper;
    private final TeamRequestRepository teamRequestRepository;
    private final UserTeamRequestRepository userTeamRequestRepository;
    private final TeamRequestMapper teamRequestMapper;
    private final UserTeamRequestMapper userTeamRequestMapper;

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO request) {
        log.info("Creating team request for team: {}", request.getTeamName());

        // Validate hackathon
        Hackathon hackathon = hackathonRepository
                .findById(request.getHackathonId())
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        // Validate leader
        User leader = userRepository
                .findById(request.getLeaderId())
                .orElseThrow(() -> new ResourceNotFoundException("Leader not found"));

        // Check if leader already has pending request for this hackathon
        boolean hasPendingRequest =
                teamRequestRepository.existsByLeaderAndHackathonAndStatus(leader, hackathon, Status.PENDING);
        if (hasPendingRequest) {
            throw new InvalidInputException("Leader already has a pending team request for this hackathon");
        }

        TeamRequest teamRequest = teamRequestMapper.toEntity(request);
        teamRequest.setHackathon(hackathon);
        teamRequest.setLeader(leader);
        teamRequest.setStatus(Status.PENDING);

        teamRequest = teamRequestRepository.save(teamRequest);
        return teamRequestMapper.toDTO(teamRequest);
    }

    @Override
    public TeamRequestDTO approveTeamRequest(Long requestId, Long reviewerId) {
        log.info("Approving team request: {}", requestId);

        TeamRequest request = teamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only approve PENDING requests");
        }

        User reviewer = userRepository
                .findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        // Create team
        Team team = Team.builder()
                .name(request.getTeamName())
                .bio(request.getDescription())
                .hackathon(request.getHackathon())
                .teamLeader(request.getLeader())
                .build();
        team = teamRepository.save(team);

        // Add leader as team member
        UserTeam leaderMembership =
                UserTeam.builder().team(team).user(request.getLeader()).build();
        userTeamRepository.save(leaderMembership);

        // Update request status
        request.setStatus(Status.APPROVED);
        request.setReviewedBy(reviewer);
        request.setReviewedAt(LocalDateTime.now());

        request = teamRequestRepository.save(request);
        return teamRequestMapper.toDTO(request);
    }

    @Override
    public TeamRequestDTO rejectTeamRequest(Long requestId, Long reviewerId) {
        log.info("Rejecting team request: {}", requestId);

        TeamRequest request = teamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only reject PENDING requests");
        }

        User reviewer = userRepository
                .findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        request.setStatus(Status.REJECTED);
        request.setReviewedBy(reviewer);
        request.setReviewedAt(LocalDateTime.now());

        request = teamRequestRepository.save(request);
        return teamRequestMapper.toDTO(request);
    }

    @Override
    public UserTeamRequestDTO inviteMember(Long teamId, Long userId, Long inviterId) {
        log.info("Creating invitation for user {} to team {}", userId, teamId);

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User inviter = userRepository
                .findById(inviterId)
                .orElseThrow(() -> new ResourceNotFoundException("Inviter not found"));

        // Validate inviter is team leader
        Long teamLeaderId = team.getTeamLeader().getId();
        if (!teamLeaderId.equals(inviter.getId())) {
            throw new InvalidInputException("Only team leader can invite members");
        }

        // Check if user is already a member
        if (userTeamRepository.existsByTeamAndUser(team, user)) {
            throw new InvalidInputException("User is already a team member");
        }

        // Check if there's already a pending invitation
        if (userTeamRequestRepository.existsByTeamAndUserAndStatusAndRequestType(
                team, user, Status.PENDING, "INVITATION")) {
            throw new InvalidInputException("User already has a pending invitation");
        }

        UserTeamRequest invitation = UserTeamRequest.builder()
                .team(team)
                .user(user)
                .requestType("INVITATION")
                .status(Status.PENDING)
                .build();

        invitation = userTeamRequestRepository.save(invitation);
        return userTeamRequestMapper.toDTO(invitation);
    }

    @Override
    public UserTeamRequestDTO acceptInvitation(Long requestId, Long userId) {
        log.info("Accepting invitation: {}", requestId);

        UserTeamRequest request = userTeamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        if (request.getUser().getId() != (userId)) {
            throw new InvalidInputException("Only invited user can accept invitation");
        }

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only accept PENDING invitations");
        }

        if (!"INVITATION".equals(request.getRequestType())) {
            throw new InvalidInputException("This is not an invitation request");
        }

        // Add user to team
        UserTeam membership = UserTeam.builder()
                .team(request.getTeam())
                .user(request.getUser())
                .build();
        userTeamRepository.save(membership);

        // Update request status
        request.setStatus(Status.APPROVED);
        request = userTeamRequestRepository.save(request);

        return userTeamRequestMapper.toDTO(request);
    }

    @Override
    public UserTeamRequestDTO rejectInvitation(Long requestId, Long userId) {
        log.info("Rejecting invitation: {}", requestId);

        UserTeamRequest request = userTeamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        if (request.getUser().getId() != (userId)) {
            throw new InvalidInputException("Only invited user can reject invitation");
        }

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only reject PENDING invitations");
        }

        if (!"INVITATION".equals(request.getRequestType())) {
            throw new InvalidInputException("This is not an invitation request");
        }

        request.setStatus(Status.REJECTED);
        request = userTeamRequestRepository.save(request);

        return userTeamRequestMapper.toDTO(request);
    }

    @Override
    public UserTeamRequestDTO requestToJoinTeam(Long teamId, Long userId) {
        log.info("Processing join request for user {} to team {}", userId, teamId);

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if user is already a member
        if (userTeamRepository.existsByTeamAndUser(team, user)) {
            throw new InvalidInputException("User is already a team member");
        }

        // Check if there's already a pending request
        if (userTeamRequestRepository.existsByTeamAndUserAndStatusAndRequestType(
                team, user, Status.PENDING, "JOIN_REQUEST")) {
            throw new InvalidInputException("User already has a pending join request");
        }

        UserTeamRequest joinRequest = UserTeamRequest.builder()
                .team(team)
                .user(user)
                .requestType("JOIN_REQUEST")
                .status(Status.PENDING)
                .build();

        joinRequest = userTeamRequestRepository.save(joinRequest);
        return userTeamRequestMapper.toDTO(joinRequest);
    }

    @Override
    public UserTeamRequestDTO approveJoinRequest(Long requestId, Long leaderId) {
        log.info("Approving join request: {}", requestId);

        UserTeamRequest request = userTeamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Join request not found"));

        // Validate leader
        if (request.getTeam().getTeamLeader().getId() != (leaderId)) {
            throw new InvalidInputException("Only team leader can approve join requests");
        }

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only approve PENDING requests");
        }

        // Add user to team
        UserTeam membership = UserTeam.builder()
                .team(request.getTeam())
                .user(request.getUser())
                .build();
        userTeamRepository.save(membership);

        // Update request status
        request.setStatus(Status.APPROVED);
        request = userTeamRequestRepository.save(request);

        return userTeamRequestMapper.toDTO(request);
    }

    @Override
    public UserTeamRequestDTO rejectJoinRequest(Long requestId, Long leaderId) {
        log.info("Rejecting join request: {}", requestId);

        UserTeamRequest request = userTeamRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Join request not found"));

        if ((leaderId == null) || (request.getTeam().getTeamLeader().getId() != (leaderId))) {
            throw new InvalidInputException("Only team leader can reject join requests");
        }

        if (!request.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Can only reject PENDING requests");
        }

        if (!"JOIN_REQUEST".equals(request.getRequestType())) {
            throw new InvalidInputException("This is not a join request");
        }

        request.setStatus(Status.REJECTED);
        request = userTeamRequestRepository.save(request);

        return userTeamRequestMapper.toDTO(request);
    }

    @Override
    public TeamDTO updateTeam(TeamDTO teamDTO) {
        log.info("Updating team: {}", teamDTO.getId());

        Team team = teamRepository
                .findById(teamDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        // Only update allowed fields
        if (teamDTO.getName() != null) {
            team.setName(teamDTO.getName());
        }
        if (teamDTO.getBio() != null) {
            team.setBio(teamDTO.getBio());
        }

        team = teamRepository.save(team);
        return teamMapper.toDTO(team);
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        log.info("Getting team by id: {}", id);
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        return teamMapper.toDTO(team);
    }

    @Override
    public List<TeamDTO> getTeams(Specification<Team> spec) {
        log.info("Getting teams with specification");

        List<Team> teams = teamRepository.findAll(spec);
        if (teams.isEmpty()) {
            throw new ResourceNotFoundException("No teams found");
        }
        return teams.stream().map(teamMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void removeMember(UserTeamDTO memberDTO) {
        Optional<UserTeam> userTeamOptional =
                userTeamRepository.findByTeamIdAndUserId(memberDTO.getTeamId(), memberDTO.getUserId());

        UserTeam userTeam = userTeamOptional.orElseThrow(() -> new ResourceNotFoundException("User not found in team"));

        Long leaderUserId = Long.valueOf(userTeam.getTeam().getTeamLeader().getId());
        if (leaderUserId.equals(memberDTO.getUserId())) {
            throw new InvalidInputException("Cannot remove team leader");
        }

        userTeamRepository.delete(userTeam);
    }

    @Override
    public TeamDTO registerForHackathon(TeamDTO teamDTO) {
        log.info("Registering team {} for hackathon {}", teamDTO.getId(), teamDTO.getHackathonId());

        Team team = teamRepository
                .findById(teamDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        Hackathon hackathon = hackathonRepository
                .findById(teamDTO.getHackathonId())
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        // Validate team leader
        Long leaderUserId = Long.valueOf(team.getTeamLeader().getId());
        if (!leaderUserId.equals(teamDTO.getTeamLeaderId())) {
            throw new InvalidInputException("Only team leader can register for hackathon");
        }

        // Validate team size
        //        long teamSize = userTeamRepository.countByTeam(team);
        //        if (teamSize < hackathon.getMinTeamSize()) {
        //            throw new InvalidInputException(String.format(
        //                    "Team size (%d) is less than minimum required (%d)", teamSize,
        // hackathon.getMinTeamSize()));
        //        }
        //        if (teamSize > hackathon.getMaxTeamSize()) {
        //            throw new InvalidInputException(
        //                    String.format("Team size (%d) exceeds maximum allowed (%d)", teamSize,
        // hackathon.getMaxTeamSize()));
        //        }
        //
        //        // Check if hackathon is full
        //        long registeredTeams = teamRepository.countByHackathon(hackathon);
        //        if (registeredTeams >= hackathon.getMaxTeams()) {
        //            throw new InvalidInputException("Hackathon has reached maximum number of teams");
        //        }

        team.setHackathon(hackathon);
        team = teamRepository.save(team);

        return teamMapper.toDTO(team);
    }
}
