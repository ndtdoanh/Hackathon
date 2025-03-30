package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.repository.*;
import com.hacof.hackathon.service.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

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

    //    @Override
    //    public List<TeamDTO> getTeamsByHackathon(long hackathonId) {
    //        return teamRepository.findByHackathonId(hackathonId).stream()
    //                .map(teamMapper::toDto)
    //                .collect(Collectors.toList());
    //    }
}
