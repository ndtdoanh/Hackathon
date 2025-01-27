package com.hacof.hackathon.service.impl;

import java.util.List;

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
    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::convertToDTO).toList();
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        return teamRepository
                .findById(id)
                .map(teamMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = teamMapper.convertToEntity(teamDTO);
        User leader = userRepository
                .findById(teamDTO.getLeaderId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        team.setLeader(leader);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.convertToDTO(savedTeam);
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam =
                teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        existingTeam.setName(teamDTO.getName());
        Team updatedTeam = teamRepository.save(existingTeam);
        return teamMapper.convertToDTO(updatedTeam);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        teamRepository.delete(team);
    }

    @Override
    public void inviteMember(Long teamId, String memberEmail) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member = userRepository
                .findByEmail(memberEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        team.getMembers().add(member);
        teamRepository.save(team);
    }

    @Override
    public void assignMentor(Long teamId, Long mentorId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        Mentor mentor = mentorRepository
                .findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        team.getMentors().add(mentor);
        teamRepository.save(team);
    }
}
