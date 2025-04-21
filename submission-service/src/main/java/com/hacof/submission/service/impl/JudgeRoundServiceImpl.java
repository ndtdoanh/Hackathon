package com.hacof.submission.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.entity.JudgeRound;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.User;
import com.hacof.submission.mapper.JudgeRoundMapper;
import com.hacof.submission.repository.JudgeRoundRepository;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.JudgeRoundService;

@Service
public class JudgeRoundServiceImpl implements JudgeRoundService {

    @Autowired
    private JudgeRoundRepository judgeRoundRepository;

    @Autowired
    private JudgeRoundMapper judgeRoundMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Override
    public JudgeRoundResponseDTO createJudgeRound(JudgeRoundRequestDTO dto) {
        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));

        User judge = userRepository
                .findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("No judges available. Please add judges to the hackathon first."));

        boolean exists = judgeRoundRepository.existsByJudgeIdAndRoundId(judge.getId(), round.getId());
        if (exists) {
            throw new IllegalArgumentException("Judge already assigned to this round");
        }

        JudgeRound judgeRound = new JudgeRound();
        judgeRound.setJudge(judge);
        judgeRound.setRound(round);

        judgeRound = judgeRoundRepository.save(judgeRound);
        return judgeRoundMapper.toResponseDTO(judgeRound);
    }

    @Override
    public JudgeRoundResponseDTO updateJudgeRound(Long id, JudgeRoundRequestDTO dto) {
        JudgeRound judgeRound = judgeRoundRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeRound not found"));

        User judge = userRepository
                .findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));
        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));

        judgeRound.setJudge(judge);
        judgeRound.setRound(round);
        judgeRound = judgeRoundRepository.save(judgeRound);
        return judgeRoundMapper.toResponseDTO(judgeRound);
    }

    @Override
    public boolean deleteJudgeRound(Long id) {
        Optional<JudgeRound> judgeRoundOptional = judgeRoundRepository.findById(id);
        if (judgeRoundOptional.isPresent()) {
            JudgeRound judgeRound = judgeRoundOptional.get();
            judgeRound.setDeleted(true);
            judgeRoundRepository.save(judgeRound);
            return true;
        }
        return false;
    }

    @Override
    public JudgeRoundResponseDTO getJudgeRound(Long id) {
        JudgeRound judgeRound = judgeRoundRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeRound not found"));

        return judgeRoundMapper.toResponseDTO(judgeRound);
    }

    @Override
    public List<JudgeRoundResponseDTO> getAllJudgeRounds() {
        List<JudgeRound> judgeRounds = judgeRoundRepository.findAll();
        return judgeRounds.stream().map(judgeRoundMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public JudgeRoundResponseDTO updateJudgeRoundByJudgeId(Long id, JudgeRoundRequestDTO dto) {
        JudgeRound judgeRound = judgeRoundRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeRound not found"));

        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));

        User judge = userRepository
                .findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("No judges available. Please add judges to the hackathon first."));

        // Check nếu đổi sang 1 cặp judge-round đã tồn tại
        boolean isDuplicate = judgeRoundRepository.existsByJudgeIdAndRoundId(judge.getId(), round.getId())
                && !(
                Long.valueOf(judgeRound.getJudge().getId()).equals(judge.getId()) &&
                        Long.valueOf(judgeRound.getRound().getId()).equals(round.getId())
        );

        if (isDuplicate) {
            throw new IllegalArgumentException("Judge already assigned to this round");
        }

        // Update thông tin
        judgeRound.setJudge(judge);
        judgeRound.setRound(round);

        judgeRound = judgeRoundRepository.save(judgeRound);
        return judgeRoundMapper.toResponseDTO(judgeRound);
    }

    @Override
    public List<JudgeRoundResponseDTO> getJudgeRoundsByRoundId(Long roundId) {
        List<JudgeRound> judgeRounds = judgeRoundRepository.findByRoundId(roundId);

        return judgeRounds.stream().map(judgeRoundMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteJudgeRoundByJudgeIdAndRoundId(Long judgeId, Long roundId) {
        Optional<JudgeRound> judgeRoundOptional = judgeRoundRepository.findByJudgeIdAndRoundId(judgeId, roundId);

        if (judgeRoundOptional.isPresent()) {
            JudgeRound judgeRound = judgeRoundOptional.get();
            judgeRoundRepository.delete(judgeRound);
        } else {
            throw new IllegalArgumentException(
                    "JudgeRound not found for judgeId: " + judgeId + " and roundId: " + roundId);
        }
    }
}
