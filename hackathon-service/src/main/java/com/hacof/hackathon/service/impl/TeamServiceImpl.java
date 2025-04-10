package com.hacof.hackathon.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.*;
import com.hacof.hackathon.dto.*;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.mapper.manual.TeamMapperManual;
import com.hacof.hackathon.repository.*;
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
        List<TeamDTO> createdTeams = new ArrayList<>();
        List<IndividualRegistrationRequest> approvedRequests =
                individualRegistrationRequestRepository.findAllByStatus(IndividualRegistrationRequestStatus.PENDING);

        List<User> users = approvedRequests.stream()
                .map(IndividualRegistrationRequest::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> eligibleUsers =
                users.stream().filter(user -> user.getStatus() == Status.ACTIVE).collect(Collectors.toList());

        Random random = new Random();
        for (int i = 0; i < eligibleUsers.size(); i += 4) {
            int teamSize = Math.min(4 + random.nextInt(3), eligibleUsers.size() - i); // Team size between 4 and 6
            List<User> teamMembers = new ArrayList<>(eligibleUsers.subList(i, i + teamSize));

            // Create and save the team
            Team team = new Team();
            team.setName("Team " + UUID.randomUUID().toString());
            teamRepository.save(team);

            // Assign members to the team
            for (User member : teamMembers) {
                UserTeam userTeam = new UserTeam();
                userTeam.setUser(member);
                userTeam.setTeam(team);
                userTeamRepository.save(userTeam);
            }

            // Assign team leader
            User teamLeader = (teamLeaderId != null && !teamLeaderId.isEmpty())
                    ? userRepository
                            .findById(Long.parseLong(teamLeaderId))
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + teamLeaderId))
                    : teamMembers.get(random.nextInt(teamMembers.size()));
            team.setTeamLeader(teamLeader);
            teamRepository.save(team);

            // Create related entities
            createRelatedEntitiesForTeam(team, teamMembers);

            // Convert to DTO and add to the result list
            createdTeams.add(TeamMapperManual.toDto(team));
        }

        return createdTeams;
    }

    private void createRelatedEntitiesForTeam(Team team, List<User> teamMembers) {
        // Example for creating a Schedule entity
        Schedule schedule = new Schedule();
        schedule.setTeam(team);
        schedule.setName(team.getName() + " Schedule");
        schedule.setDescription("Schedule for " + team.getName());
        scheduleRepository.save(schedule);

        // Repeat similar steps for the other entities: Board, BoardUser, Conversation,
        // ConversationUser, TeamRound, TeamHackathon, etc.
    }

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

            Team team = TeamMapperManual.toEntity(new TeamDTO());
            team.setTeamLeader(teamLeader);
            team.setName("Team " + teamLeader.getUsername());

            team = teamRepository.save(team);

            UserTeam teamLeaderUserTeam = new UserTeam();
            teamLeaderUserTeam.setUser(teamLeader);
            teamLeaderUserTeam.setTeam(team);
            teamMemberRepository.save(teamLeaderUserTeam);

            for (TeamMemberBulkDTO member : request.getTeamMembers()) {
                if (member.getUserId().equals(request.getTeamLeaderId())) {
                    continue;
                }

                User user = userRepository
                        .findById(Long.parseLong(member.getUserId()))
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                UserTeam userTeam = new UserTeam();
                userTeam.setUser(user);
                userTeam.setTeam(team);
                teamMemberRepository.save(userTeam);
            }

            for (TeamHackathonBulkDTO hackathonDTO : request.getTeamHackathons()) {
                Hackathon hackathon = hackathonRepository
                        .findById(Long.parseLong(hackathonDTO.getHackathonId()))
                        .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

                TeamHackathon teamHackathon = TeamHackathon.builder()
                        .team(team)
                        .hackathon(hackathon)
                        .status(TeamHackathonStatus.valueOf(hackathonDTO.getStatus()))
                        .build();

                teamHackathonRepository.save(teamHackathon);
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
