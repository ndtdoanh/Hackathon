package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.CompetitionRoundDTO;
import com.hacof.hackathon.entity.CompetitionRound;
import com.hacof.hackathon.entity.Judge;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.CompetitionRoundMapper;
import com.hacof.hackathon.repository.CompetitionRoundRepository;
import com.hacof.hackathon.repository.JudgeRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.CompetitionRoundService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompetitionRoundServiceImpl implements CompetitionRoundService {
    private final CompetitionRoundMapper roundMapper;
    private final CompetitionRoundRepository roundRepository;
    private final JudgeRepository judgeRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public List<CompetitionRoundDTO> getAllRounds() {
        return roundRepository.findAll().stream().map(roundMapper::convertToDTO).toList();
    }

    @Override
    public CompetitionRoundDTO getRoundById(Long id) {
        return roundRepository
                .findById(id)
                .map(roundMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));
    }

    @Override
    public CompetitionRoundDTO createRound(CompetitionRoundDTO roundDTO) {
        CompetitionRound round = roundMapper.convertToEntity(roundDTO);
        CompetitionRound savedRound = roundRepository.save(round);
        return roundMapper.convertToDTO(savedRound);
    }

    @Override
    public CompetitionRoundDTO updateRound(Long id, CompetitionRoundDTO roundDTO) {
        CompetitionRound existingRound =
                roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        existingRound.setName(roundDTO.getName());
        existingRound.setDescription(roundDTO.getDescription());
        existingRound.setMaxTeam(roundDTO.getMaxTeam());
        CompetitionRound updatedRound = roundRepository.save(existingRound);
        return roundMapper.convertToDTO(updatedRound);
    }

    @Override
    public void deleteRound(Long id) {
        CompetitionRound round =
                roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        roundRepository.delete(round);
    }

    @Override
    public void assignJudgeToRound(Long roundId, Long judgeId) {
        CompetitionRound round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        Judge judge =
                judgeRepository.findById(judgeId).orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
        // Logic to assign judge to round
    }

    @Override
    public void assignTaskToMember(Long teamId, Long memberId, String task) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member =
                userRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Logic to assign task to member
    }
}
