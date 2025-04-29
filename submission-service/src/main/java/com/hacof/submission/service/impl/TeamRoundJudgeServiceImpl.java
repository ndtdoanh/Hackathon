package com.hacof.submission.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.entity.TeamRound;
import com.hacof.submission.entity.TeamRoundJudge;
import com.hacof.submission.mapper.TeamRoundJudgeMapper;
import com.hacof.submission.repository.JudgeRoundRepository;
import com.hacof.submission.repository.TeamRoundJudgeRepository;
import com.hacof.submission.repository.TeamRoundRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.TeamRoundJudgeService;

@Service
public class TeamRoundJudgeServiceImpl implements TeamRoundJudgeService {

    @Autowired
    private TeamRoundJudgeRepository teamRoundJudgeRepository;

    @Autowired
    private TeamRoundRepository teamRoundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JudgeRoundRepository judgeRoundRepository;

    @Autowired
    private TeamRoundJudgeMapper teamRoundJudgeMapper;

    @Override
    public TeamRoundJudgeResponseDTO createTeamRoundJudge(TeamRoundJudgeRequestDTO requestDTO) {
        TeamRound teamRound = teamRoundRepository
                .findById(requestDTO.getTeamRoundId())
                .orElseThrow(() ->
                        new IllegalArgumentException("TeamRound not found with ID " + requestDTO.getTeamRoundId()));

        if ("CANCELLED".equalsIgnoreCase(String.valueOf(teamRound.getStatus()))) {
            throw new IllegalArgumentException("Cannot assign judge to inactive team");
        }

        if (teamRound.getRound().getHackathon().getEndDate() != null
                && teamRound.getRound().getHackathon().getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot assign after round ended");
        }

        boolean isJudgeInRound = judgeRoundRepository.existsByJudgeIdAndRoundId(
                requestDTO.getJudgeId(), teamRound.getRound().getId());
        if (!isJudgeInRound) {
            throw new IllegalArgumentException(
                    "Judge is not assigned to this round and cannot be added to TeamRoundJudge.");
        }

        boolean alreadyAssigned = teamRoundJudgeRepository.existsByTeamRoundIdAndJudgeId(
                requestDTO.getTeamRoundId(), requestDTO.getJudgeId());
        if (alreadyAssigned) {
            throw new IllegalArgumentException("Judge already assigned to this team");
        }

        // Proceed to create
        TeamRoundJudge entity = teamRoundJudgeMapper.toEntity(requestDTO, teamRoundRepository, userRepository);
        TeamRoundJudge savedEntity = teamRoundJudgeRepository.save(entity);
        return teamRoundJudgeMapper.toResponseDTO(savedEntity);
    }

    @Override
    public TeamRoundJudgeResponseDTO updateTeamRoundJudge(Long id, TeamRoundJudgeRequestDTO requestDTO) {
        Optional<TeamRoundJudge> teamRoundJudgeOptional = teamRoundJudgeRepository.findById(id);
        if (!teamRoundJudgeOptional.isPresent()) {
            throw new IllegalArgumentException("TeamRoundJudge not found with ID " + id);
        }

        TeamRoundJudge teamRoundJudge = teamRoundJudgeOptional.get();

        teamRoundJudgeMapper.updateEntityFromDTO(requestDTO, teamRoundJudge);

        teamRoundJudge = teamRoundJudgeRepository.save(teamRoundJudge);

        return teamRoundJudgeMapper.toResponseDTO(teamRoundJudge);
    }

    @Override
    public void deleteTeamRoundJudge(Long id) {
        if (!teamRoundJudgeRepository.existsById(id)) {
            throw new IllegalArgumentException("TeamRoundJudge not found with ID " + id);
        }
        teamRoundJudgeRepository.deleteById(id);
    }

    @Override
    public TeamRoundJudgeResponseDTO getTeamRoundJudgeById(Long id) {
        TeamRoundJudge entity = teamRoundJudgeRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TeamRoundJudge not found with ID " + id));
        return teamRoundJudgeMapper.toResponseDTO(entity);
    }

    @Override
    public List<TeamRoundJudgeResponseDTO> getAllTeamRoundJudges() {
        List<TeamRoundJudge> teamRoundJudges = teamRoundJudgeRepository.findAll();

        return teamRoundJudges.stream().map(teamRoundJudgeMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeamRoundJudgeResponseDTO> getTeamRoundJudgesByTeamRoundId(Long teamRoundId) {
        List<TeamRoundJudge> teamRoundJudges = teamRoundJudgeRepository.findByTeamRoundId(teamRoundId);
        //
        //        if (teamRoundJudges.isEmpty()) {
        //            throw new IllegalArgumentException("No TeamRoundJudge found for teamRoundId: " + teamRoundId);
        //        }

        return teamRoundJudges.stream().map(teamRoundJudgeMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteTeamRoundJudgeByTeamRoundIdAndJudgeId(Long teamRoundId, Long judgeId) {
        Optional<TeamRoundJudge> teamRoundJudge =
                teamRoundJudgeRepository.findByTeamRoundIdAndJudgeId(teamRoundId, judgeId);

        if (!teamRoundJudge.isPresent()) {
            throw new IllegalArgumentException(
                    "No TeamRoundJudge found for teamRoundId: " + teamRoundId + " and judgeId: " + judgeId);
        }

        teamRoundJudgeRepository.delete(teamRoundJudge.get());
    }
}
