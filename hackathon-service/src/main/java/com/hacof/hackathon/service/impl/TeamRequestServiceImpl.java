package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.*;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestSearchDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamRequestMapper;
import com.hacof.hackathon.repository.*;
import com.hacof.hackathon.service.NotificationService;
import com.hacof.hackathon.service.TeamRequestService;
import com.hacof.hackathon.specification.TeamRequestSpecification;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class TeamRequestServiceImpl implements TeamRequestService {
    TeamRequestRepository teamRequestRepository;
    TeamRepository teamRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    TeamRequestMapper teamRequestMapper;
    NotificationService notificationService;
    BoardRepository boardRepository;
    BoardUserRepository boardUserRepository;
    ConversationRepository conversationRepository;
    ConversationUserRepository conversationUserRepository;
    ScheduleRepository scheduleRepository;
    TeamRoundRepository teamRoundRepository;
    RoundRepository roundRepository;

    // use later - pending to use Paging
    @Override
    public List<TeamRequestDTO> searchTeamRequests(TeamRequestSearchDTO searchDTO) {
        Specification<TeamRequest> spec = Specification.where(null);

        if (searchDTO.getHackathonId() != null) {
            spec = spec.and(TeamRequestSpecification.hasHackathonId(searchDTO.getHackathonId()));
        }
        if (searchDTO.getTeamName() != null) {
            spec = spec.and(TeamRequestSpecification.hasTeamName(searchDTO.getTeamName()));
        }
        if (searchDTO.getStatus() != null) {
            spec = spec.and(TeamRequestSpecification.hasStatus(searchDTO.getStatus()));
        }
        if (searchDTO.getMemberId() != null) {
            spec = spec.and(TeamRequestSpecification.hasMemberId(searchDTO.getMemberId()));
        }
        if (searchDTO.getFromDate() != null || searchDTO.getToDate() != null) {
            spec = spec.and(
                    TeamRequestSpecification.createdDateBetween(searchDTO.getFromDate(), searchDTO.getToDate()));
        }

        // Fetch all results without pagination
        return teamRequestRepository.findAll(spec).stream()
                .map(teamRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> getAllTeamRequests() {
        if (teamRequestRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No team requests found");
        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAll();
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO request) {
        // Validate hackathon
        Hackathon hackathon = validateHackathon(request.getHackathonId());
        log.debug("Validated successfully hackathon: {}", hackathon.getTitle());

        // Validate team size
        validateTeamSize(request.getTeamRequestMembers().size(), hackathon);
        log.debug(
                "Validated successfully team size: {}",
                request.getTeamRequestMembers().size());

        // Validate members not in other teams
        //        validateMembersNotInOtherTeams(request.getTeamRequestMembers(), hackathon.getId());
        //        log.debug("Đã validate members không thuộc team khác thành công");

        // Create team request
        TeamRequest teamRequest = TeamRequest.builder()
                .hackathon(hackathon)
                .name(request.getName())
                .status(TeamRequestStatus.PENDING)
                .confirmationDeadline(LocalDateTime.now().plusDays(7))
                .note(request.getNote())
                .teamRequestMembers(new ArrayList<>())
                .build();

        // Add members
        request.getTeamRequestMembers().forEach(member -> {
            User user = userRepository
                    .findById(Long.parseLong(member.getUserId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Not found with user: " + member.getUserId()));

            TeamRequestMember memberEntity = TeamRequestMember.builder()
                    .teamRequest(teamRequest)
                    .user(user)
                    .status(TeamRequestMemberStatus.PENDING)
                    .build();
            teamRequest.getTeamRequestMembers().add(memberEntity);
        });

        TeamRequest saved = teamRequestRepository.save(teamRequest);

        notificationService.notifyTeamRequestCreated(saved);
        log.debug("Send Notification Successfully!");

        return teamRequestMapper.toDto(saved);
    }

    @Override
    public TeamRequestDTO updateMemberResponse(
            String requestId, String userId, TeamRequestMemberStatus status, String note) {
        log.info("Update team request {} for request {}", userId, requestId);

        TeamRequest request = getTeamRequest(requestId);

        // Validate request still pending
        if (request.getStatus() != TeamRequestStatus.PENDING) {
            throw new IllegalStateException("Just can update response for pending request");
        }

        // Update member response
        TeamRequestMember member = request.getTeamRequestMembers().stream()
                .filter(m -> m.getUser().getId() == (Long.parseLong(userId)))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Not found member in team request"));

        member.setStatus(status);
        member.setNote(note);
        member.setRespondedAt(LocalDateTime.now());

        // Check if all members have responded
        boolean allResponded = request.getTeamRequestMembers().stream()
                .allMatch(m -> m.getStatus() != TeamRequestMemberStatus.PENDING);

        boolean allApproved = request.getTeamRequestMembers().stream()
                .allMatch(m -> m.getStatus() == TeamRequestMemberStatus.APPROVED);

        log.debug("Status respond - All responded: {}, All approved: {}", allResponded, allApproved);

        if (allResponded) {
            if (allApproved) {
                request.setStatus(TeamRequestStatus.UNDER_REVIEW);
                log.info("All members approved, change to UNDER_REVIEW");
            } else {
                request.setStatus(TeamRequestStatus.REJECTED);
                log.info("Have another member reject, change to REJECTED");
            }
        }

        TeamRequest updated = teamRequestRepository.save(request);
        notificationService.notifyMemberResponse(member);

        return teamRequestMapper.toDto(updated);
    }
    // Organizer review team request
    @Override
    public TeamRequestDTO reviewTeamRequest(String requestId, TeamRequestStatus status, String note) {
        log.info("Review team request: {}", requestId);

        TeamRequest request = getTeamRequest(requestId);

        // Validate request is under review
        if (request.getStatus() != TeamRequestStatus.UNDER_REVIEW) {
            throw new InvalidInputException("Just can review request is waiting for approval");
        }

        // Validate all members approved if approving request
        if (status == TeamRequestStatus.APPROVED && !allMembersApproved(request)) {
            throw new InvalidInputException("Cannot approve request if not all members approved");
        }

        // Update request status
        request.setStatus(status);
        request.setNote(note);
        request.setReviewedAt(LocalDateTime.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidInputException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        request.setReviewedBy(currentUser);

        // If approved, create team
        if (status == TeamRequestStatus.APPROVED) {
            Team team = createTeam(request);
            log.info("Created team from request {}", requestId);

            // Create Schedule
            Schedule schedule = Schedule.builder()
                    .team(team)
                    .hackathon(request.getHackathon())
                    .build();
            scheduleRepository.save(schedule);
            log.debug("Created schedule for team {}", team.getId());

            // Create Board
            Board board =
                    Board.builder().team(team).owner(request.getCreatedBy()).build();
            boardRepository.save(board);
            log.debug("Created board for team {}", team.getId());

            // Create BoardUsers
            for (UserTeam userTeam : team.getTeamMembers()) {
                BoardUser boardUser = BoardUser.builder()
                        .board(board)
                        .user(userTeam.getUser())
                        .role(
                                userTeam.getUser().getId()
                                                == team.getTeamLeader().getId()
                                        ? BoardUserRole.ADMIN
                                        : BoardUserRole.MEMBER)
                        .build();
                boardUserRepository.save(boardUser);
            }
            log.debug("Created board users for team {}", team.getId());

            // Create Conversation
            Conversation conversation = Conversation.builder()
                    .team(team)
                    .type(ConversationType.PRIVATE)
                    .name(team.getName())
                    .build();
            conversationRepository.save(conversation);
            log.debug("Created conversation for team {}", team.getId());

            // Create ConversationUsers
            for (UserTeam userTeam : team.getTeamMembers()) {
                ConversationUser conversationUser = ConversationUser.builder()
                        .user(userTeam.getUser())
                        .conversation(conversation)
                        .isDeleted(false)
                        .build();
                conversationUserRepository.save(conversationUser);
            }
            log.debug("Created conversation users for team {}", team.getId());

            // Create TeamRound
            Round round = roundRepository
                    .findFirstByHackathonIdOrderByRoundNumberAsc(
                            request.getHackathon().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No rounds found for hackathon"));
            TeamRound teamRound = TeamRound.builder()
                    .team(team)
                    .round(round)
                    .status(TeamRoundStatus.PENDING)
                    .description("Team " + team.getName() + " registered for round " + round.getRoundNumber())
                    .build();
            teamRoundRepository.save(teamRound);
            log.debug("Created team round for team {}", team.getId());
        }

        TeamRequest updated = teamRequestRepository.save(request);
        notificationService.notifyTeamRequestReviewed(updated);

        return teamRequestMapper.toDto(updated);
    }

    @Override
    public List<TeamRequestDTO> getAllByHackathonIdAndUserId(String hackathonId, String userId) {
        if (hackathonId == null || userId == null) {
            throw new IllegalArgumentException("Hackathon ID and User ID must not be null");
        }
        if (teamRequestRepository
                .findAllByHackathonIdAndUserId(Long.parseLong(hackathonId), Long.parseLong(userId))
                .isEmpty()) {
            throw new ResourceNotFoundException("No team requests found for the given Hackathon ID and User ID");
        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathonIdAndUserId(
                Long.parseLong(hackathonId), Long.parseLong(userId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        if (teamRequestRepository.findAllByUserId(Long.parseLong(userId)).isEmpty()) {
            throw new ResourceNotFoundException("No team requests found for the given User ID");
        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByUserId(Long.parseLong(userId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByHackathonId(String hackathonId) {
        if (hackathonId == null) {
            throw new IllegalArgumentException("Hackathon ID must not be null");
        }
        if (teamRequestRepository
                .findAllByHackathonId(Long.parseLong(hackathonId))
                .isEmpty()) {
            throw new ResourceNotFoundException("No team requests found for the given Hackathon ID");
        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathonId(Long.parseLong(hackathonId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTeamRequest(Long teamRequestId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("TeamRequest not found with ID " + teamRequestId));
        teamRequestRepository.delete(teamRequest);
    }

    private Team createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .teamLeader(request.getCreatedBy()) // Set leader is the creator that send request
                .teamMembers(new HashSet<>())
                .teamHackathons(new ArrayList<>())
                .isDeleted(false)
                .build();

        // add new team
        TeamHackathon teamHackathon = TeamHackathon.builder()
                .team(team)
                .hackathon(request.getHackathon())
                .status(TeamHackathonStatus.ACTIVE)
                .build();
        team.getTeamHackathons().add(teamHackathon);

        // add members to team
        request.getTeamRequestMembers().forEach(member -> {
            UserTeam userTeam = UserTeam.builder()
                    .team(team)
                    .user(member.getUser())
                    .status(Status.ACTIVE)
                    .build();
            team.getTeamMembers().add(userTeam);
        });

        return teamRepository.save(team);
    }

    private TeamRequest getTeamRequest(String requestId) {
        return teamRequestRepository
                .findById(Long.parseLong(requestId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu tạo team"));
    }

    private boolean allMembersApproved(TeamRequest request) {
        return request.getTeamRequestMembers().stream()
                .allMatch(member -> member.getStatus() == TeamRequestMemberStatus.APPROVED);
    }

    private void validateTeamSize(int size, Hackathon hackathon) {
        if (size < hackathon.getMinTeamSize() || size > hackathon.getMaxTeamSize()) {
            throw new IllegalArgumentException(String.format(
                    "Số lượng thành viên phải từ %d đến %d người",
                    hackathon.getMinTeamSize(), hackathon.getMaxTeamSize()));
        }
    }

    private Hackathon validateHackathon(String hackathonId) {
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(hackathonId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hackathon"));

        if (hackathon.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Hackathon không trong thời gian đăng ký");
        }

        return hackathon;
    }
}
