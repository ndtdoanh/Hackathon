package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO, Long creatorId) {
        User creator =
                userRepository.findById(creatorId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Team team = teamMapper.convertToEntity(teamDTO);
        team = teamRepository.save(team);

        return teamMapper.convertToDTO(team);
    }

    @Override
    public TeamDTO inviteMemberToTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return teamMapper.convertToDTO(team);
    }

    @Override
    public TeamDTO requestToJoinTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return teamMapper.convertToDTO(team);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll().stream().map(teamMapper::convertToDTO).collect(Collectors.toList());
    }
}
