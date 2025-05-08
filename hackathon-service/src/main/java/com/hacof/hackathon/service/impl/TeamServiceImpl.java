package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.BoardUserRole;
import com.hacof.hackathon.constant.ConversationType;
import com.hacof.hackathon.constant.TeamHackathonStatus;
import com.hacof.hackathon.constant.TeamRoundStatus;
import com.hacof.hackathon.dto.TeamBulkRequestDTO;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamHackathonBulkDTO;
import com.hacof.hackathon.dto.TeamMemberBulkDTO;
import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.BoardUser;
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
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.ScheduleRepository;
import com.hacof.hackathon.repository.TeamHackathonRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.repository.UserTeamRepository;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.specification.TeamSpecification;
import com.hacof.hackathon.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true)
@Slf4j
public class TeamServiceImpl implements TeamService {
    UserRepository userRepository;
    TeamRepository teamRepository;
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
    TeamMapper teamMapper;
    SecurityUtil securityUtil;

    @Override
    public TeamDTO updateTeam(long id, TeamDTO teamDTO) {
        Team existingTeam =
                teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        if (!existingTeam.getName().equals(teamDTO.getName()) && teamRepository.existsByName(teamDTO.getName())) {
            throw new InvalidInputException("Team name already exists: " + teamDTO.getName());
        }

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

            // Validate unique team name
            String teamName = "Team " + teamLeader.getUsername();
            if (teamRepository.existsByName(teamName)) {
                throw new InvalidInputException("Team name already exists: " + teamName);
            }

            // Create Team
            Team team = TeamMapperManual.toEntity(new TeamDTO());
            team.setTeamLeader(teamLeader);
            team.setName("Team " + teamLeader.getUsername());
            team = teamRepository.save(team);
            team.setTeamMembers(new HashSet<>());

            // Create UserTeam entry for Team Leader
            UserTeam teamLeaderUserTeam = new UserTeam();
            teamLeaderUserTeam.setUser(teamLeader);
            teamLeaderUserTeam.setTeam(team);
            teamMemberRepository.save(teamLeaderUserTeam);
            team.getTeamMembers().add(teamLeaderUserTeam);

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
                team.getTeamMembers().add(userTeam);
            }

            // Retrieve hackathon (assumes all hackathons in the list are the same)
            Hackathon hackathon = hackathonRepository
                    .findById(request.getTeamHackathons().stream()
                            .findFirst()
                            .map(TeamHackathonBulkDTO::getHackathonId)
                            .map(Long::parseLong)
                            .orElseThrow(() -> new ResourceNotFoundException("No hackathon ID provided")))
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
            User currentUser = securityUtil.getAuthenticatedUser();

            // Create Board entry only if it doesn't exist
            if (!boardRepository.existsByTeam(team)) {
                Board board = Board.builder()
                        .team(team)
                        .owner(teamLeader) // Set createdBy as teamLeader
                        .name(team.getName() + " Board")
                        .description("Board for team " + team.getName())
                        .hackathon(hackathon)
                        .build();
                board = boardRepository.save(board);

                // Create BoardUser entries for all team members
                for (UserTeam userTeam : team.getTeamMembers()) {
                    User user = userTeam.getUser();

                    BoardUser boardUser = BoardUser.builder()
                            .board(board)
                            .user(user)
                            .role(
                                    user.getId() == team.getTeamLeader().getId()
                                            ? BoardUserRole.ADMIN
                                            : BoardUserRole.MEMBER)
                            .build();
                    boardUserRepository.save(boardUser);
                }
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

                // check if ConversationUser already exists
                boolean exists = conversationUserRepository.existsByConversationAndUser(conversation, user);
                log.debug(
                        "Checking if conversation user exists: Conversation ID = {}, User ID = {}, Exists = {}",
                        conversation.getId(),
                        user.getId(),
                        exists);

                if (!exists) {
                    // Create ConversationUser entry
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

    @Override
    public List<TeamDTO> getTeamsByHackathonId(Long hackathonId) {
        hackathonRepository
                .findById(hackathonId)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found with id: " + hackathonId));

        List<Team> teams = teamRepository.findByHackathonId(hackathonId);
        return teams.stream().map(TeamMapperManual::toDto).collect(Collectors.toList());
    }
}
