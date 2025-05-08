package com.hacof.hackathon.service.impl;

import static com.hacof.hackathon.mapper.manual.TeamRequestMapperManual.toDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.BoardUserRole;
import com.hacof.hackathon.constant.ConversationType;
import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.constant.TeamHackathonStatus;
import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.constant.TeamRoundStatus;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.BoardUser;
import com.hacof.hackathon.entity.Conversation;
import com.hacof.hackathon.entity.ConversationUser;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Role;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.Schedule;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamHackathon;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserRole;
import com.hacof.hackathon.entity.UserTeam;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.TeamRequestMapperManual;
import com.hacof.hackathon.repository.BoardRepository;
import com.hacof.hackathon.repository.BoardUserRepository;
import com.hacof.hackathon.repository.ConversationRepository;
import com.hacof.hackathon.repository.ConversationUserRepository;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.RoleRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.ScheduleRepository;
import com.hacof.hackathon.repository.TeamHackathonRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRequestMemberRepository;
import com.hacof.hackathon.repository.TeamRequestRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.repository.UserTeamRepository;
import com.hacof.hackathon.service.NotificationService;
import com.hacof.hackathon.service.TeamRequestService;
import com.hacof.hackathon.util.SecurityUtil;

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
    RoleRepository roleRepository;
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
    NotificationService notificationService;
    SecurityUtil securityUtil;

    @Override
    public List<TeamRequestDTO> getTeamRequestsByMemberIdAndHackathonId(Long memberId, Long hackathonId) {
        return teamRequestRepository.findByMemberIdAndHackathonId(memberId, hackathonId).stream()
                .map(TeamRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> getAllTeamRequests() {
        List<TeamRequest> teamRequests = teamRequestRepository.findAll();
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO request) {
        Hackathon hackathon = validateHackathon(request.getHackathonId());
        validateEnrollPeriod(hackathon);
        validateTeamSize(request.getTeamRequestMembers().size(), hackathon);

        User currentUser = getCurrentUser();

        if (teamRepository.existsByName(request.getName())) {
            throw new InvalidInputException("Team name already exists: " + request.getName());
        }

        // Extract and validate userIds
        Set<String> userIds = extractAndValidateUserIds(request, currentUser);

        // Validate users are not in an approved team
        validateUsersNotInApprovedTeam(userIds, hackathon);

        TeamRequest teamRequest = TeamRequest.builder()
                .hackathon(hackathon)
                .name(request.getName())
                .status(TeamRequestStatus.PENDING)
                .confirmationDeadline(LocalDateTime.now().plusDays(7))
                .note(request.getNote())
                .teamRequestMembers(new ArrayList<>())
                .build();

        userIds.forEach(userId -> {
            User user = userRepository
                    .findById(Long.parseLong(userId))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            TeamRequestMember memberEntity = TeamRequestMember.builder()
                    .teamRequest(teamRequest)
                    .user(user)
                    .status(
                            user.getId() == currentUser.getId()
                                    ? TeamRequestMemberStatus.APPROVED // Assign APPROVED for the creator
                                    : TeamRequestMemberStatus.PENDING) // Assign PENDING for others
                    .build();
            teamRequest.getTeamRequestMembers().add(memberEntity);
        });

        TeamRequest saved = teamRequestRepository.save(teamRequest);

        // Update the current user's role to TEAM_LEADER
        updateUserRoleToTeamLeader(currentUser);

        // Send notifications (will use bulk email)
        notificationService.notifyTeamRequestCreated(saved);

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

        User currentUser = securityUtil.getAuthenticatedUser();

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
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByUserId(Long.parseLong(userId));
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByHackathonId(String hackathonId) {
        if (hackathonId == null || hackathonId.isBlank()) {
            throw new IllegalArgumentException("Hackathon ID must not be null or blank");
        }

        long id;
        try {
            id = Long.parseLong(hackathonId);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Hackathon ID must be a valid number");
        }

        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathon_Id(id);
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTeamRequest(Long teamRequestId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("TeamRequest not found with ID " + teamRequestId));
        teamRequestRepository.delete(teamRequest);
    }

    @Override
    public List<TeamRequestDTO> getTeamRequestsByMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("Member ID must not be null");
        }
        List<TeamRequest> teamRequests = teamRequestRepository.findByTeamRequestMembers_User_Id(memberId);
        return teamRequests.stream().map(TeamRequestMapperManual::toDto).collect(Collectors.toList());
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

        if (hackathon.getStatus() != Status.ACTIVE && hackathon.getStatus() != Status.ONGOING) {
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

    private void updateUserRoleToTeamLeader(User user) {
        // Fetch the "TEAM_LEADER" role
        Role teamLeaderRole = roleRepository
                .findByName("TEAM_LEADER")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'TEAM_LEADER' not found"));

        // Remove the "TEAM_MEMBER" role if it exists
        user.getUserRoles()
                .removeIf(userRole -> "TEAM_MEMBER".equals(userRole.getRole().getName()));

        // Add the "TEAM_LEADER" role
        UserRole teamLeaderUserRole = UserRole.builder().role(teamLeaderRole).build();
        teamLeaderUserRole.setUser(user); // Use setter to assign the user
        user.getUserRoles().add(teamLeaderUserRole);
        // Save the updated user
        userRepository.save(user);
    }

    private Set<String> extractAndValidateUserIds(TeamRequestDTO request, User currentUser) {
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
        return userIds;
    }

    private void validateUsersNotInApprovedTeam(Set<String> userIds, Hackathon hackathon) {
        for (String userId : userIds) {
            boolean exists = teamRequestRepository.existsApprovedTeamRequestByUserIdAndHackathonId(
                    Long.parseLong(userId), hackathon.getId());

            if (exists) {
                throw new InvalidInputException(
                        "User with ID " + userId + " already belongs to an approved team in this hackathon.");
            }
        }
    }

    private void validateEnrollPeriod(Hackathon hackathon) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(hackathon.getEnrollStartDate()) || now.isAfter(hackathon.getEnrollEndDate())) {
            throw new InvalidInputException("Cannot create a team request outside the enrollment period");
        }
    }
}
