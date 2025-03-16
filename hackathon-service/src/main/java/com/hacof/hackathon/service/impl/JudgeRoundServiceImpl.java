package com.hacof.hackathon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.JudgeRoundDTO;
import com.hacof.hackathon.entity.JudgeRound;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.JudgeRoundMapper;
import com.hacof.hackathon.repository.JudgeRoundRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.JudgeRoundService;

@Service
public class JudgeRoundServiceImpl implements JudgeRoundService {

    @Autowired
    private JudgeRoundRepository judgeRoundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private JudgeRoundMapper judgeRoundMapper;

    @Override
    public JudgeRoundDTO assignJudgeToRound(Long judgeId, Long roundId) {
        User judge =
                userRepository.findById(judgeId).orElseThrow(() -> new ResourceNotFoundException("Judge not found"));
        Round round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        JudgeRound judgeRound = new JudgeRound();
        judgeRound.setJudge(judge);
        judgeRound.setRound(round);

        judgeRound = judgeRoundRepository.save(judgeRound);
        return judgeRoundMapper.toDTO(judgeRound);
    }
}
