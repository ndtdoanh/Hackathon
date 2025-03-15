package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Mentor;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.repository.MentorRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.TeamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final TeamMapper teamMapper;

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO, Long userId) {
        User leader =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Team team = teamMapper.convertToEntity(teamDTO);
        //  team.setLeader(leader);

        if (team.getMembers() == null) {
            team.setMembers(new ArrayList<>());
        }

        team.getMembers().add(leader);
        team.setCreatedBy(leader.getUsername());

        Team savedTeam = teamRepository.save(team);
        return teamMapper.convertToDTO(savedTeam);
    }

    @Override
    public TeamDTO addMemberToTeam(Long teamId, Long memberId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member =
                userRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        team.getMembers().add(member);
        Team updatedTeam = teamRepository.save(team);
        return teamMapper.convertToDTO(updatedTeam);
    }

    @Override
    public TeamDTO assignMentorToTeam(Long teamId, Long mentorId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        Mentor mentor = mentorRepository
                .findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        team.setMentor(mentor);
        Team updatedTeam = teamRepository.save(team);
        return teamMapper.convertToDTO(updatedTeam);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TeamDTO updateTeam(Long teamId, TeamDTO teamDTO) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        team.setName(teamDTO.getName());
        Team updatedTeam = teamRepository.save(team);
        return teamMapper.convertToDTO(updatedTeam);
    }

    @Override
    public void removeMemberFromTeam(Long teamId, Long memberId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member =
                userRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        team.getMembers().remove(member);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Team not found");
        }
        teamRepository.deleteById(teamId);
    }
}
