package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamRoundJudgeDTO;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.entity.TeamRoundJudge;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.TeamRoundJudgeMapperManual;
import com.hacof.hackathon.repository.TeamRoundJudgeRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.TeamRoundJudgeService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class TeamRoundJudgeServiceImpl implements TeamRoundJudgeService {
    TeamRoundJudgeRepository teamRoundJudgeRepository;
    TeamRoundRepository teamRoundRepository;
    UserRepository userRepository;

    @Override
    public TeamRoundJudgeDTO create(TeamRoundJudgeDTO dto) {
        TeamRound teamRound = teamRoundRepository
                .findById(Long.parseLong(dto.getTeamRoundId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team Round not found"));

        User judge = userRepository
                .findById(Long.parseLong(dto.getJudgeId()))
                .orElseThrow(() -> new ResourceNotFoundException("Judge not found"));

        TeamRoundJudge entity = TeamRoundJudgeMapperManual.toEntity(dto);
        entity.setTeamRound(teamRound);
        entity.setJudge(judge);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastModifiedDate(LocalDateTime.now());

        TeamRoundJudge saved = teamRoundJudgeRepository.save(entity);
        return TeamRoundJudgeMapperManual.toDto(saved);
    }

    @Override
    public TeamRoundJudgeDTO update(Long id, TeamRoundJudgeDTO dto) {
        TeamRoundJudge entity = teamRoundJudgeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Round Judge not found"));

        TeamRound teamRound = teamRoundRepository
                .findById(Long.parseLong(dto.getTeamRoundId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team Round not found"));

        User judge = userRepository
                .findById(Long.parseLong(dto.getJudgeId()))
                .orElseThrow(() -> new ResourceNotFoundException("Judge not found"));

        // update fields
        entity.setTeamRound(teamRound);
        entity.setJudge(judge);
        // entity.setCreatedBy(dto.getLastModifiedByUserName());
        entity.setCreatedDate(LocalDateTime.now());

        TeamRoundJudge saved = teamRoundJudgeRepository.save(entity);
        return TeamRoundJudgeMapperManual.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!teamRoundJudgeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team Round Judge not found");
        }
        teamRoundJudgeRepository.deleteById(id);
    }

    @Override
    public List<TeamRoundJudgeDTO> getAll() {
        return teamRoundJudgeRepository.findAll().stream()
                .map(TeamRoundJudgeMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamRoundJudgeDTO getById(Long id) {
        TeamRoundJudge entity = teamRoundJudgeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Round Judge not found"));
        return TeamRoundJudgeMapperManual.toDto(entity);
    }
}
