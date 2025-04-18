package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.ConversationType;
import com.hacof.hackathon.constant.TeamHackathonStatus;
import com.hacof.hackathon.constant.TeamRoundStatus;
import com.hacof.hackathon.dto.TeamBulkRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamHackathonBulkDTO;
import com.hacof.hackathon.dto.TeamMemberBulkDTO;
import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.Conversation;
import com.hacof.hackathon.entity.ConversationUser;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.Schedule;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamHackathon;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeam;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.mapper.manual.TeamMapperManual;
import com.hacof.hackathon.repository.BoardRepository;
import com.hacof.hackathon.repository.BoardUserRepository;
import com.hacof.hackathon.repository.ConversationRepository;
import com.hacof.hackathon.repository.ConversationUserRepository;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.IndividualRegistrationRequestRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.ScheduleRepository;
import com.hacof.hackathon.repository.TeamHackathonRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.repository.UserTeamRepository;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.specification.TeamSpecification;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true)
@Slf4j
public class TeamServiceImpl implements TeamService {
    IndividualRegistrationRequestRepository individualRegistrationRequestRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;
    TeamMapper teamMapper;
    UserTeamRepository userTeamRepository;
    HackathonRepository hackathonRepository;
    UserTeamRepository teamMemberRepository;
    TeamHackathonRepository teamHackathonRepository;
    BoardRepository boardRepository;
    BoardUserRepository boardUserRepository;
    ConversationRepository conversationRepository;
    ConversationUserRepository conversationUserRepository;
    ScheduleRepository scheduleRepository;
    TeamRoundRepository teamRoundRepository;
    RoundRepository roundRepository;

    // Create Bulk Teams
    @Override
    public List<TeamDTO> createBulkTeams(String teamLeaderId, List<Long> userIds) {
        //        List<TeamDTO> createdTeams = new ArrayList<>();
        //        List<IndividualRegistrationRequest> approvedRequests =
        //
        // individualRegistrationRequestRepository.findAllByStatus(IndividualRegistrationRequestStatus.PENDING);
        //
        //        List<User> users = approvedRequests.stream()
        //                .map(IndividualRegistrationRequest::getCreatedBy)
        //                .distinct()
        //                .collect(Collectors.toList());
        //
        //        List<User> eligibleUsers =
        //                users.stream().filter(user -> user.getStatus() == Status.ACTIVE).collect(Collectors.toList());
        //
        //        Random random = new Random();
        //        for (int i = 0; i < eligibleUsers.size(); i += 4) {
        //            int teamSize = Math.min(4 + random.nextInt(3), eligibleUsers.size() - i); // Team size between 4
        // and 6
        //            List<User> teamMembers = new ArrayList<>(eligibleUsers.subList(i, i + teamSize));
        //
        //            // Create and save the team
        //            Team team = new Team();
        //            team.setName("Team " + UUID.randomUUID().toString());
        //            teamRepository.save(team);
        //
        //            // Assign members to the team
        //            for (User member : teamMembers) {
        //                UserTeam userTeam = new UserTeam();
        //                userTeam.setUser(member);
        //                userTeam.setTeam(team);
        //                userTeamRepository.save(userTeam);
        //            }
        //
        //            // Assign team leader
        //            User teamLeader = (teamLeaderId != null && !teamLeaderId.isEmpty())
        //                    ? userRepository
        //                            .findById(Long.parseLong(teamLeaderId))
        //                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " +
        // teamLeaderId))
        //                    : teamMembers.get(random.nextInt(teamMembers.size()));
        //            team.setTeamLeader(teamLeader);
        //            teamRepository.save(team);
        //
        //            // Create related entities
        //            createRelatedEntitiesForTeam(team, teamMembers);
        //
        //            // Convert to DTO and add to the result list
        //            createdTeams.add(TeamMapperManual.toDto(team));
        //        }

        return null;
    }

    //    private void createRelatedEntitiesForTeam(Team team, List<User> teamMembers) {
    //        // Example for creating a Schedule entity
    //        Schedule schedule = new Schedule();
    //        schedule.setTeam(team);
    //        schedule.setName(team.getName() + " Schedule");
    //        schedule.setDescription("Schedule for " + team.getName());
    //        scheduleRepository.save(schedule);
    //
    //        // Repeat similar steps for the other entities: Board, BoardUser, Conversation,
    //        // ConversationUser, TeamRound, TeamHackathon, etc.
    //    }

    //    public void createRelatedEntitiesForTeam(Team team, List<User> teamMembers) {
    //        Hackathon hackathon = hackathonRepository
    //                .findById(team.getHackathon().getId())
    //                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    //
    //        // 1. Schedule
    //        if (!scheduleRepository.existsByTeamAndHackathon(team, hackathon)) {
    //            Schedule schedule = Schedule.builder()
    //                    .team(team)
    //                    .hackathon(hackathon)
    //                    .name(team.getName() + " Schedule")
    //                    .description("Schedule for " + team.getName())
    //                    .build();
    //            scheduleRepository.save(schedule);
    //        }
    //
    //        // 2. Board
    //        Board board = boardRepository.findByTeamId(team.getId()).orElse(null);
    //        if (board == null) {
    //            board = Board.builder()
    //                    .team(team)
    //                    .owner(team.getTeamLeader())
    //                    .name(team.getName() + " Board")
    //                    .description("Board for team " + team.getName())
    //                    .hackathon(hackathon)
    //                    .build();
    //            boardRepository.save(board);
    //        }
    //
    //        // 3. BoardUser
    //        for (User user : teamMembers) {
    //            if (!boardUserRepository.existsByBoardAndUser(board, user)) {
    //                BoardUser boardUser = BoardUser.builder()
    //                        .board(board)
    //                        .user(user)
    //                        .role(
    //                                user.getId().equals(team.getTeamLeader().getId())
    //                                        ? BoardUserRole.ADMIN
    //                                        : BoardUserRole.MEMBER)
    //                        .isDeleted(false)
    //                        .build();
    //                boardUserRepository.save(boardUser);
    //            }
    //        }
    //
    //        // 4. Conversation
    //        Conversation conversation =
    //                conversationRepository.findByTeamId(team.getId()).orElse(null);
    //        if (conversation == null) {
    //            conversation = Conversation.builder()
    //                    .team(team)
    //                    .type(ConversationType.PRIVATE)
    //                    .name(team.getName())
    //                    .hackathon(hackathon)
    //                    .build();
    //            conversationRepository.save(conversation);
    //        }
    //
    //        // 5. ConversationUser
    //        for (User user : teamMembers) {
    //            if (!conversationUserRepository.existsByConversationAndUser(conversation, user)) {
    //                ConversationUser conversationUser = ConversationUser.builder()
    //                        .user(user)
    //                        .conversation(conversation)
    //                        .isDeleted(false)
    //                        .build();
    //                conversationUserRepository.save(conversationUser);
    //            }
    //        }
    //
    //        // 6. TeamRound
    //        Round round = roundRepository
    //                .findFirstByHackathonIdOrderByRoundNumberAsc(hackathon.getId())
    //                .orElseThrow(() -> new ResourceNotFoundException("No round found for hackathon"));
    //        TeamRound teamRound = TeamRound.builder()
    //                .team(team)
    //                .round(round)
    //                .status(TeamRoundStatus.PENDING)
    //                .description("Team " + team.getName() + " registered for round " + round.getRoundNumber())
    //                .build();
    //        teamRoundRepository.save(teamRound);
    //
    //        // 7. TeamHackathon
    //        if (!teamHackathonRepository.existsByTeamAndHackathon(team, hackathon)) {
    //            TeamHackathon teamHackathon = TeamHackathon.builder()
    //                    .team(team)
    //                    .hackathon(hackathon)
    //                    .status(TeamHackathonStatus.ACTIVE)
    //                    .build();
    //            teamHackathonRepository.save(teamHackathon);
    //        }
    //    }

    @Override
    public TeamDTO updateTeam(long id, TeamDTO teamDTO) {
        Team existingTeam =
                teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        existingTeam.setName(teamDTO.getName());
        existingTeam.setBio(teamDTO.getBio());
        existingTeam.setDeleted(teamDTO.isDeleted());
        existingTeam.setDeletedById(teamDTO.getDeletedById() != null ? Long.parseLong(teamDTO.getDeletedById()) : null);

        Team updatedTeam = teamRepository.save(existingTeam);
        return teamMapper.toDto(updatedTeam);
    }

    @Override
    public void deleteTeam(long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        teamRepository.delete(team);
    }

    @Override
    public TeamDTO getTeamById(long id) {
        return teamRepository
                .findById(id)
                .map(TeamMapperManual::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(TeamMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId) {
        if (userId == null || hackathonId == null) {
            throw new InvalidInputException("UserId and HackathonId cannot be null");
        }

        Specification<Team> spec = TeamSpecification.hasUserIdAndHackathonId(userId, hackathonId);
        List<Team> teams = teamRepository.findAll(spec);
        return teams.stream().map(TeamMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamDTO> createBulkTeams(List<TeamBulkRequestDTO> bulkRequest) {
        List<TeamDTO> createdTeams = new ArrayList<>();

        for (TeamBulkRequestDTO request : bulkRequest) {
            User teamLeader = userRepository
                    .findById(Long.parseLong(request.getTeamLeaderId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Team leader not found"));

            // Create Team
            Team team = TeamMapperManual.toEntity(new TeamDTO());
            team.setTeamLeader(teamLeader);
            team.setName("Team " + teamLeader.getUsername());
            team = teamRepository.save(team);

            // Create UserTeam entry for Team Leader
            UserTeam teamLeaderUserTeam = new UserTeam();
            teamLeaderUserTeam.setUser(teamLeader);
            teamLeaderUserTeam.setTeam(team);
            teamMemberRepository.save(teamLeaderUserTeam);

            if (team.getTeamMembers() == null) {
                team.setTeamMembers(new HashSet<>());
            }

            // Add Team Members excluding Team Leader
            for (TeamMemberBulkDTO member : request.getTeamMembers()) {
                if (member.getUserId().equals(request.getTeamLeaderId())) {
                    continue; // Skip team leader as member
                }

                User user = userRepository
                        .findById(Long.parseLong(member.getUserId()))
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                UserTeam userTeam = new UserTeam();
                userTeam.setUser(user);
                userTeam.setTeam(team);
                teamMemberRepository.save(userTeam);
            }

            // Retrieve hackathon (assumes all hackathons in the list are the same)
            Hackathon hackathon = hackathonRepository
                    .findById(Long.parseLong(request.getTeamHackathons().get(0).getHackathonId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

            // Create Hackathon associations for the team
            for (TeamHackathonBulkDTO hackathonDTO : request.getTeamHackathons()) {
                TeamHackathon teamHackathon = TeamHackathon.builder()
                        .team(team)
                        .hackathon(hackathon)
                        .status(TeamHackathonStatus.valueOf(hackathonDTO.getStatus()))
                        .build();

                teamHackathonRepository.save(teamHackathon);
            }

            // Create Schedule entry only if it doesn't exist
            if (!scheduleRepository.existsByTeamAndHackathon(team, hackathon)) {
                Schedule schedule = Schedule.builder()
                        .team(team)
                        .hackathon(hackathon)
                        .name(team.getName() + " Schedule")
                        .description("Schedule for " + team.getName())
                        .build();
                scheduleRepository.save(schedule);
            }

            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new InvalidInputException("No authenticated user found");
            }

            String username = authentication.getName();
            User currentUser = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

            // Create Board entry only if it doesn't exist
            if (!boardRepository.existsByTeam(team)) {
                Board board = Board.builder()
                        .team(team)
                        .owner(currentUser)
                        .name(team.getName() + " Board")
                        .description("Board for team " + team.getName())
                        .hackathon(hackathon)
                        .build();
                boardRepository.save(board);
            }

            // Create TeamRound entry only if it doesn't exist
            Round round = roundRepository
                    .findFirstByHackathonIdOrderByRoundNumberAsc(hackathon.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No rounds found for hackathon"));

            TeamRound teamRound = TeamRound.builder()
                    .team(team)
                    .round(round)
                    .status(TeamRoundStatus.PENDING)
                    .description("Team " + team.getName() + " registered for round " + round.getRoundNumber())
                    .build();

            teamRoundRepository.save(teamRound);

            // Create Conversation entry only if it doesn't exist
            if (!conversationRepository.existsByTeam(team)) {
                Conversation conversation = Conversation.builder()
                        .team(team)
                        .type(ConversationType.PRIVATE)
                        .name(team.getName())
                        .hackathon(hackathon)
                        .build();
                conversationRepository.save(conversation);
            }

            // Create ConversationUser entries only if they don't exist
            Conversation conversation = conversationRepository
                    .findByTeamId(team.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));

            for (UserTeam userTeam : team.getTeamMembers()) {
                User user = userTeam.getUser();

                // Kiểm tra xem ConversationUser đã tồn tại chưa
                boolean exists = conversationUserRepository.existsByConversationAndUser(conversation, user);
                log.debug(
                        "Checking if conversation user exists: Conversation ID = {}, User ID = {}, Exists = {}",
                        conversation.getId(),
                        user.getId(),
                        exists);

                if (!exists) {
                    // Tạo ConversationUser mới nếu không tồn tại
                    ConversationUser conversationUser = ConversationUser.builder()
                            .user(user)
                            .conversation(conversation)
                            .isDeleted(false)
                            .build();
                    conversationUserRepository.save(conversationUser);
                    log.debug(
                            "Created ConversationUser for user {} in conversation {}",
                            user.getUsername(),
                            conversation.getId());
                } else {
                    log.debug(
                            "ConversationUser already exists for user {} in conversation {}",
                            user.getUsername(),
                            conversation.getId());
                }
            }

            createdTeams.add(TeamMapperManual.toDto(team));
        }

        return createdTeams;
    }

    //    @Override
    //    public List<TeamDTO> updateBulkTeams(List<TeamBulkRequestDTO> bulkRequest) {
    //        List<TeamDTO> updatedTeams = new ArrayList<>();
    //
    //        for (TeamBulkRequestDTO request : bulkRequest) {
    //            Team existingTeam = teamRepository.findById(Long.parseLong(request.getTeamLeaderId())) // Modify this
    // to fetch by Team ID instead of Team Leader ID
    //                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    //
    //            User teamLeader = userRepository.findById(Long.parseLong(request.getTeamLeaderId()))
    //                    .orElseThrow(() -> new ResourceNotFoundException("Team leader not found"));
    //            existingTeam.setTeamLeader(teamLeader);
    //
    //            for (TeamMemberBulkDTO member : request.getTeamMembers()) {
    //                User user = userRepository.findById(Long.parseLong(member.getUserId()))
    //                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    //
    //                Optional<UserTeam> userTeamOpt = teamMemberRepository.findByUserIdAndTeamId(user.getId(),
    // existingTeam.getId());
    //                if (userTeamOpt.isPresent()) {
    //                    UserTeam existingUserTeam = userTeamOpt.get();
    //                } else {
    //                    UserTeam userTeam = new UserTeam();
    //                    userTeam.setUser(user);
    //                    userTeam.setTeam(existingTeam);
    //                    teamMemberRepository.save(userTeam);
    //                }
    //            }
    //
    //            for (TeamHackathonBulkDTO hackathonDTO : request.getTeamHackathons()) {
    //                Hackathon hackathon = hackathonRepository.findById(Long.parseLong(hackathonDTO.getHackathonId()))
    //                        .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    //
    //                Optional<TeamHackathon> teamHackathonOpt =
    // teamHackathonRepository.findByTeamIdAndHackathonId(existingTeam.getId(), hackathon.getId());
    //                if (teamHackathonOpt.isPresent()) {
    //                    TeamHackathon teamHackathon = teamHackathonOpt.get();
    //                    teamHackathon.setStatus(TeamHackathonStatus.valueOf(hackathonDTO.getStatus()));
    //                    teamHackathonRepository.save(teamHackathon);
    //                } else {
    //                    TeamHackathon teamHackathon = new TeamHackathon();
    //                    teamHackathon.setTeam(existingTeam);
    //                    teamHackathon.setHackathon(hackathon);
    //                    teamHackathon.setStatus(TeamHackathonStatus.valueOf(hackathonDTO.getStatus()));
    //                    teamHackathonRepository.save(teamHackathon);
    //                }
    //            }
    //
    //            teamRepository.save(existingTeam);
    //
    //            updatedTeams.add(teamMapper.toDto(existingTeam));  // Using injected TeamMapper
    //
    //        }
    //        return updatedTeams;
    //    }

    //    @Override
    //    public List<TeamDTO> getTeamsByHackathon(long hackathonId) {
    //        return teamRepository.findByHackathonId(hackathonId).stream()
    //                .map(teamMapper::toDto)
    //                .collect(Collectors.toList());
    //    }
}
