package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.JudgeDTO;
import com.hacof.hackathon.entity.CompetitionRound;
import com.hacof.hackathon.entity.Judge;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.JudgeMapper;
import com.hacof.hackathon.repository.CompetitionRoundRepository;
import com.hacof.hackathon.repository.JudgeRepository;
import com.hacof.hackathon.service.JudgeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JudgeServiceImpl implements JudgeService {
    private final JudgeMapper judgeMapper;
    private final JudgeRepository judgeRepository;
    private final CompetitionRoundRepository roundRepository;

    @Override
    public List<JudgeDTO> getAllJudges() {
        return judgeRepository.findAll().stream().map(judgeMapper::convertToDTO).toList();
    }

    @Override
    public JudgeDTO getJudgeById(Long id) {
        return judgeRepository
                .findById(id)
                .map(judgeMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
    }

    @Override
    public JudgeDTO createJudge(JudgeDTO judgeDTO) {
        Judge judge = judgeMapper.convertToEntity(judgeDTO);
        Judge savedJudge = judgeRepository.save(judge);
        return judgeMapper.convertToDTO(savedJudge);
    }

    @Override
    public JudgeDTO updateJudge(Long id, JudgeDTO judgeDTO) {
        Judge existingJudge =
                judgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
        existingJudge.setName(judgeDTO.getName());
        existingJudge.setEmail(judgeDTO.getEmail());
        Judge updatedJudge = judgeRepository.save(existingJudge);
        return judgeMapper.convertToDTO(updatedJudge);
    }

    @Override
    public void deleteJudge(Long id) {
        Judge judge = judgeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
        judgeRepository.delete(judge);
    }

    @Override
    public void assignJudgeToRound(Long judgeId, Long roundId) {
        Judge judge =
                judgeRepository.findById(judgeId).orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
        CompetitionRound round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        if (!round.getJudges().contains(judge)) {
            round.getJudges().add(judge);
            roundRepository.save(round);
        }
    }
}
