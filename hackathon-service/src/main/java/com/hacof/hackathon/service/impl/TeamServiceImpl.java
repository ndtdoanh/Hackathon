package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.*;
import com.hacof.hackathon.dto.TeamDTO;
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

    //    @Override
    //    public List<TeamDTO> getTeamsByHackathon(long hackathonId) {
    //        return teamRepository.findByHackathonId(hackathonId).stream()
    //                .map(teamMapper::toDto)
    //                .collect(Collectors.toList());
    //    }
}
