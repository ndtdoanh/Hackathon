package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.repository.*;
import com.hacof.hackathon.service.TeamService;
import com.hacof.hackathon.specification.TeamSpecification;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true)
public class TeamServiceImpl implements TeamService {
    IndividualRegistrationRequestRepository individualRegistrationRequestRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;
    TeamMapper teamMapper;

    @Override
    public TeamDTO createTeamWithParticipants(String teamName, List<Long> requestIds) {
        // Fetch individual registration requests
        List<IndividualRegistrationRequest> requests = individualRegistrationRequestRepository.findAllById(requestIds);

        // Extract user IDs from the individual registration requests
        List<Long> userIds =
                requests.stream().map(request -> request.getCreatedBy().getId()).collect(Collectors.toList());

        // Fetch users by their IDs
        List<User> participants = userRepository.findAllById(userIds);

        // Ensure participants do not already belong to a team
        List<User> eligibleParticipants = participants.stream()
                .filter(user -> user.getUserTeams().isEmpty())
                .collect(Collectors.toList());

        if (eligibleParticipants.size() < 4) {
            throw new IllegalArgumentException("Not enough participants without a team");
        }

        // Create a new team
        Team team = new Team();
        team.setName(teamName);
        teamRepository.save(team);

        // Assign participants to the new team
        eligibleParticipants.forEach(participant -> {
            UserTeam userTeam = new UserTeam();
            userTeam.setUser(participant);
            userTeam.setTeam(team);
            participant.getUserTeams().add(userTeam);
            userRepository.save(participant);
        });

        // Randomly select one participant as the team leader
        Random random = new Random();
        User teamLeader = eligibleParticipants.get(random.nextInt(eligibleParticipants.size()));
        team.setTeamLeader(teamLeader);
        teamRepository.save(team);

        return teamMapper.toDto(team);
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        return teamMapper.toDto(teamRepository.save(team));
    }

    @Override
    public TeamDTO updateTeam(long id, TeamDTO teamDTO) {
        Team existingTeam =
                teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        existingTeam.setName(teamDTO.getName());
        existingTeam.setBio(teamDTO.getBio());
        return teamMapper.toDto(teamRepository.save(existingTeam));
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
                .map(teamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamDTO> getTeamsByUserIdAndHackathonId(Long userId, Long hackathonId) {
        Specification<Team> spec = TeamSpecification.hasLeaderIdAndHackathonId(userId, hackathonId);
        List<Team> teams = teamRepository.findAll(spec);
        return teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    //    @Override
    //    public List<TeamDTO> getTeamsByHackathon(long hackathonId) {
    //        return teamRepository.findByHackathonId(hackathonId).stream()
    //                .map(teamMapper::toDto)
    //                .collect(Collectors.toList());
    //    }
}
