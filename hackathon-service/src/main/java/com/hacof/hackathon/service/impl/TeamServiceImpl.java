package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.UserTeamDTO;
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
@Slf4j
public class TeamServiceImpl implements TeamService {
    IndividualRegistrationRequestRepository individualRegistrationRequestRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;
    TeamMapper teamMapper;


    @Override
    public List<TeamDTO> createBulkTeams(List<Long> userIds) {
        log.debug("Starting createBulkTeams with userIds: {}", userIds);
        List<TeamDTO> createdTeams = new ArrayList<>();
        List<User> users = userRepository.findAllById(userIds);
        log.debug("Fetched users: {}", users);

        // Adjust the filtering condition as needed
        List<User> eligibleUsers = users.stream()
                .filter(user -> user.getStatus() == Status.ACTIVE) 
                .collect(Collectors.toList());
        log.debug("Eligible users: {}", eligibleUsers);

        Random random = new Random();
        for (int i = 0; i < eligibleUsers.size(); i += 4) {
            int teamSize =
                    Math.min(4 + random.nextInt(3), eligibleUsers.size() - i); // Random team size between 4 and 6
            List<User> teamMembers = new ArrayList<>(eligibleUsers.subList(i, i + teamSize));
            log.debug("Creating team with members: {}", teamMembers);

            // Create a new team
            Team team = new Team();
            team.setName("Team " + UUID.randomUUID().toString());
            teamRepository.save(team);
            log.debug("Created team: {}", team);

            // Assign members to the team
            teamMembers.forEach(member -> {
                UserTeam userTeam = new UserTeam();
                userTeam.setUser(member);
                userTeam.setTeam(team);
                member.getUserTeams().add(userTeam);
                userRepository.save(member);
                log.debug("Assigned user {} to team {}", member, team);
            });

            // Randomly select a team leader
            User teamLeader = teamMembers.get(random.nextInt(teamMembers.size()));
            team.setTeamLeader(teamLeader);
            teamRepository.save(team);
            log.debug("Set team leader: {}", teamLeader);

            //            if (!teamMembers.isEmpty() && !teamMembers.get(0).getUserHackathons().isEmpty()) {
            //                team.setTeamHackathons(List.of(new TeamHackathon(team,
            // teamMembers.get(0).getUserHackathons().iterator().next().getHackathon())));
            //            }

//            TeamDTO teamDTO = teamMapper.toDto(team);
//            teamDTO.setTeamLeaderId(teamLeader.getId().toString());
//            if (!teamMembers.isEmpty()
//                    && !teamMembers.get(0).getUserHackathons().isEmpty()) {
//                teamDTO.setHackathonId(String.valueOf(teamMembers
//                        .get(0)
//                        .getUserHackathons()
//                        .iterator()
//                        .next()
//                        .getHackathon()
//                        .getId()));
//            }
//            teamDTO.setTeamMembers(teamMembers.stream()
//                    .map(member -> new UserTeamDTO(null, member.getId(), team.getId(), null, null, null, null))
//                    .collect(Collectors.toSet()));
//
//            createdTeams.add(teamDTO);
            TeamDTO teamDTO = teamMapper.toDto(team);
            teamDTO.setTeamLeaderId(String.valueOf(teamLeader.getId()));
            if (!teamMembers.isEmpty() && !teamMembers.get(0).getUserHackathons().isEmpty()) {
                teamDTO.setHackathonId(String.valueOf(teamMembers.get(0).getUserHackathons().iterator().next().getHackathon().getId()));            }
            teamDTO.setTeamMembers(teamMembers.stream()
                    .map(member -> new UserTeamDTO(null, member.getId(), team.getId(), null, null, null, null))
                    .collect(Collectors.toSet()));

            createdTeams.add(teamDTO);
            log.debug("Added team to createdTeams: {}", teamDTO);
        }

        log.debug("Finished createBulkTeams with createdTeams: {}", createdTeams);
        return createdTeams;
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
