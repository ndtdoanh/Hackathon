package com.hacof.hackathon.service.impl;

import static com.hacof.hackathon.mapper.manual.TeamRequestMapperManual.toDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.*;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.dto.TeamRequestSearchDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamRequestMapper;
import com.hacof.hackathon.mapper.manual.TeamRequestMapperManual;
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
    TeamRequestMemberRepository teamRequestMemberRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    UserTeamRepository userTeamRepository;
    TeamHackathonRepository teamHackathonRepository;
    BoardRepository boardRepository;
    BoardUserRepository boardUserRepository;
    ConversationRepository conversationRepository;
    ConversationUserRepository conversationUserRepository;
    ScheduleRepository scheduleRepository;
    TeamRoundRepository teamRoundRepository;
    RoundRepository roundRepository;
    EmailServiceImpl emailService;
    NotificationService notificationService;
    TeamRequestMapper teamRequestMapper;

    @Override
    public List<TeamRequestDTO> getTeamRequestsByMemberIdAndHackathonId(Long memberId, Long hackathonId) {
        return teamRequestRepository.findByMemberIdAndHackathonId(memberId, hackathonId).stream()
                .map(TeamRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

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
        //        if (teamRequestRepository.findAll().isEmpty()) {
        //            throw new ResourceNotFoundException("No team requests found");
        //        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAll();
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO request) {
        Hackathon hackathon = validateHackathon(request.getHackathonId());
        validateTeamSize(request.getTeamRequestMembers().size(), hackathon);

        User currentUser = getCurrentUser();

        // validate userIds
        Set<String> userIds = new HashSet<>();
        for (TeamRequestMemberDTO member : request.getTeamRequestMembers()) {
            if (member.getUserId() == null || member.getUserId().trim().isEmpty()) {
                throw new InvalidInputException("UserId cannot be null or empty");
            }
            if (!userIds.add(member.getUserId())) {
                throw new InvalidInputException("Duplicate userId: " + member.getUserId());
            }
        }

        userIds.add(String.valueOf(currentUser.getId()));

        TeamRequest teamRequest = TeamRequest.builder()
                .hackathon(hackathon)
                .name(request.getName())
                .status(TeamRequestStatus.PENDING)
                .confirmationDeadline(LocalDateTime.now().plusDays(7))
                .note(request.getNote())
                .teamRequestMembers(new ArrayList<>())
                .build();

        //        request.getTeamRequestMembers().forEach(member -> {
        //            if (member.getUserId() == null || member.getUserId().trim().isEmpty()) {
        //                log.error("UserId is null for member: {}", member);
        //                throw new InvalidInputException("UserId cannot be null");
        //            }
        //
        //            User user = userRepository
        //                    .findById(Long.parseLong(member.getUserId()))
        //                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " +
        // member.getUserId()));
        //
        //            TeamRequestMember memberEntity = TeamRequestMember.builder()
        //                    .teamRequest(teamRequest)
        //                    .user(user)
        //                    .status(TeamRequestMemberStatus.PENDING)
        //                    .build();
        //            teamRequest.getTeamRequestMembers().add(memberEntity);
        //        });

        userIds.forEach(userId -> {
            User user = userRepository
                    .findById(Long.parseLong(userId))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            TeamRequestMember memberEntity = TeamRequestMember.builder()
                    .teamRequest(teamRequest)
                    .user(user)
                    .status(TeamRequestMemberStatus.PENDING)
                    .build();
            teamRequest.getTeamRequestMembers().add(memberEntity);
        });

        TeamRequest saved = teamRequestRepository.save(teamRequest);

        // Send email to all members
        teamRequest.getTeamRequestMembers().stream()
                .map(TeamRequestMember::getUser)
                .forEach(user -> {
                    emailService.sendEmail(
                            user.getEmail(), "Team Request Created", "A new team request has been created.");
                });

        TeamRequestDTO response = toDto(saved);

        response.getTeamRequestMembers()
                .forEach(memberDTO -> memberDTO.setTeamRequestId(String.valueOf(saved.getId())));
        return response;
    }

    @Override
    public TeamRequestDTO updateMemberResponse(
            String requestId, String userId, TeamRequestMemberStatus status, String note) {
        TeamRequest request = getTeamRequest(requestId);

        if (request.getStatus() != TeamRequestStatus.PENDING) {
            throw new InvalidInputException("Just can update response for pending request");
        }

        if (userId == null || userId.trim().isEmpty()) {
            throw new InvalidInputException("UserId cannot be null");
        }

        TeamRequestMember member = request.getTeamRequestMembers().stream()
                .filter(m -> m.getUser().getId() == (Long.parseLong(userId)))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Not found member in team request"));

        member.setStatus(status);
        member.setNote(note);
        member.setRespondedAt(LocalDateTime.now());
        log.debug("Member {} responded at {}", member.getUser().getId(), member.getRespondedAt());

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

        // Save the updated team request
        TeamRequest updated = teamRequestRepository.save(request);

        // Notify member about the response update
        notificationService.notifyMemberResponse(member);

        // Return the updated team request as DTO
        TeamRequestDTO response = toDto(updated);

        // Ensure the teamRequestId is populated in the TeamRequestMemberDTOs
        response.getTeamRequestMembers()
                .forEach(memberDTO -> memberDTO.setTeamRequestId(String.valueOf(updated.getId())));

        return response;
    }

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

        log.debug("Current user: {}", currentUser.getUsername());

        request.setReviewedBy(currentUser);

        // If approved, create team
        if (status == TeamRequestStatus.APPROVED) {
            Team team = createTeam(request);
            log.info("Created team from request {}", requestId);

            // Create UserTeam entries only if they don't exist
            for (TeamRequestMember member : request.getTeamRequestMembers()) {
                if (userTeamRepository.existsByUserAndTeam(member.getUser(), team)) {
                    log.debug(
                            "UserTeam entry already exists for user {}",
                            member.getUser().getUsername());
                } else {
                    UserTeam userTeam = UserTeam.builder()
                            .user(member.getUser())
                            .team(team)
                            .status(Status.ACTIVE)
                            .build();
                    userTeamRepository.save(userTeam);
                    log.debug("Created user team for user {}", member.getUser().getUsername());
                }
            }

            // Create Schedule entry only if it doesn't exist
            if (scheduleRepository.existsByTeamAndHackathon(team, request.getHackathon())) {
                log.debug("Schedule entry already exists for team {}", team.getId());
            } else {
                Schedule schedule = Schedule.builder()
                        .team(team)
                        .hackathon(request.getHackathon())
                        .name(team.getName() + " Schedule")
                        .description("Schedule for " + team.getName())
                        .build();
                scheduleRepository.save(schedule);
                log.debug("Created schedule for team {}", team.getId());
            }

            // Create Board entry only if it doesn't exist
            if (boardRepository.existsByTeam(team)) {
                log.debug("Board entry already exists for team {}", team.getId());
            } else {
                Board board = Board.builder()
                        .team(team)
                        .owner(request.getCreatedBy())
                        .name(team.getName() + " Board")
                        .description("Board for team " + team.getName())
                        .hackathon(request.getHackathon())
                        .build();
                boardRepository.save(board);
                log.debug("Created board for team {}", team.getId());
            }

            // Fetch board for boardUser creation
            Board board = boardRepository
                    .findByTeamId(team.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Board not found for team: " + team.getId()));
            for (UserTeam userTeam : team.getTeamMembers()) {
                User user = userTeam.getUser();

                boolean exists = boardUserRepository.existsByBoardAndUser(board, user);
                if (!exists) {
                    boolean isOwner = user.getId() == (request.getCreatedBy().getId());
                    BoardUser boardUser = BoardUser.builder()
                            .board(board)
                            .user(user)
                            .role(isOwner ? BoardUserRole.ADMIN : BoardUserRole.MEMBER)
                            .isDeleted(false)
                            .build();
                    boardUserRepository.save(boardUser);
                    log.debug("Created board user for user {}", user.getUsername());
                } else {
                    log.debug("Board user already exists for user {}", user.getUsername());
                }
            }

            // Create Conversation entry only if it doesn't exist
            if (conversationRepository.existsByTeam(team)) {
                log.debug("Conversation entry already exists for team {}", team.getId());
            } else {
                Conversation conversation = Conversation.builder()
                        .team(team)
                        .type(ConversationType.PRIVATE)
                        .name(team.getName())
                        .hackathon(request.getHackathon())
                        .build();
                conversationRepository.save(conversation);
                log.debug("Created conversation for team {}", team.getId());
            }

            // Create ConversationUsers only if they don't exist
            Conversation conversation = conversationRepository
                    .findByTeamId(team.getId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Conversation not found for team: " + team.getId()));

            for (UserTeam userTeam : team.getTeamMembers()) {
                User user = userTeam.getUser();

                boolean exists = conversationUserRepository.existsByConversationAndUser(conversation, user);
                if (!exists) {
                    ConversationUser conversationUser = ConversationUser.builder()
                            .user(user)
                            .conversation(conversation)
                            .isDeleted(false)
                            .build();
                    conversationUserRepository.save(conversationUser);
                    log.debug("Created conversation user for user {}", user.getUsername());
                } else {
                    log.debug("Conversation user already exists for user {}", user.getUsername());
                }
            }

            // Create TeamRound entry only if it doesn't exist
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

            // Create TeamHackathon entry only if it doesn't exist
            if (teamHackathonRepository.existsByTeamAndHackathon(team, request.getHackathon())) {
                log.debug("TeamHackathon entry already exists for team {}", team.getId());
            } else {
                TeamHackathon teamHackathon = TeamHackathon.builder()
                        .team(team)
                        .hackathon(request.getHackathon())
                        .status(TeamHackathonStatus.ACTIVE)
                        .build();
                teamHackathonRepository.save(teamHackathon);
                log.debug("Created team hackathon for team {}", team.getId());
            }

            // Link teamRequest to each team member and save
            for (TeamRequestMember member : request.getTeamRequestMembers()) {
                member.setTeamRequest(request); // Link teamRequest to member
                teamRequestMemberRepository.save(member); // Save member with updated teamRequest
            }
        }

        TeamRequest updated = teamRequestRepository.save(request);

        notificationService.notifyTeamRequestReviewed(updated);

        return TeamRequestMapperManual.toDto(updated);
    }

    @Override
    public List<TeamRequestDTO> getAllByHackathonIdAndUserId(String hackathonId, String userId) {
        if (hackathonId == null || userId == null) {
            throw new IllegalArgumentException("Hackathon ID and User ID must not be null");
        }
        //        if (teamRequestRepository
        //                .findAllByHackathonIdAndUserId(Long.parseLong(hackathonId), Long.parseLong(userId))
        //                .isEmpty()) {
        //            throw new ResourceNotFoundException("No team requests found for the given Hackathon ID and User
        // ID");
        //        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathonIdAndUserId(
                Long.parseLong(hackathonId), Long.parseLong(userId));
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        //        if (teamRequestRepository.findAllByUserId(Long.parseLong(userId)).isEmpty()) {
        //            throw new ResourceNotFoundException("No team requests found for the given User ID");
        //        }
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByUserId(Long.parseLong(userId));
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByHackathonId(String hackathonId) {
        if (hackathonId == null || hackathonId.isBlank()) {
            throw new IllegalArgumentException("Hackathon ID must not be null or blank");
        }

        Long id;
        try {
            id = Long.parseLong(hackathonId);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Hackathon ID must be a valid number");
        }

        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathon_Id(id);

        if (teamRequests.isEmpty()) {
            throw new ResourceNotFoundException("No team requests found for Hackathon ID = " + id);
        }

        return teamRequests.stream()
                .map(TeamRequestMapperManual::toDto)
                .collect(Collectors.toList());
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
                .orElseThrow(() -> new ResourceNotFoundException("Not found with team request: " + requestId));
    }

    private boolean allMembersApproved(TeamRequest request) {
        return request.getTeamRequestMembers().stream()
                .allMatch(member -> member.getStatus() == TeamRequestMemberStatus.APPROVED);
    }

    private void validateTeamSize(int size, Hackathon hackathon) {
        if (size < hackathon.getMinTeamSize() || size > hackathon.getMaxTeamSize()) {
            throw new InvalidInputException(String.format(
                    "Team Members must be from %d to %d people",
                    hackathon.getMinTeamSize(), hackathon.getMaxTeamSize()));
        }
    }

    private Hackathon validateHackathon(String hackathonId) {
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(hackathonId))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found Hackathon"));

        if (hackathon.getStatus() != Status.ACTIVE) {
            throw new InvalidInputException("Hackathon is not active");
        }

        return hackathon;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));
    }
}
