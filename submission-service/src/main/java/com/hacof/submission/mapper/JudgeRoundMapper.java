package com.hacof.submission.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.entity.JudgeRound;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.User;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.UserRepository;

@Component
public class JudgeRoundMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    // Chuyển từ DTO sang Entity
    public JudgeRound toEntity(JudgeRoundRequestDTO dto) {
        JudgeRound judgeRound = new JudgeRound();

        User judge = userRepository
                .findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));
        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));
        judgeRound.setJudge(judge);
        judgeRound.setRound(round);

        return judgeRound;
    }

    // Chuyển từ Entity sang DTO
    public JudgeRoundResponseDTO toResponseDTO(JudgeRound entity) {
        JudgeRoundResponseDTO dto = new JudgeRoundResponseDTO();

        dto.setId(entity.getId());
        dto.setJudgeId(entity.getJudge().getId());
        dto.setRoundId(entity.getRound().getId());
        dto.setDeleted(entity.isDeleted());
        dto.setCreatedDate(entity.getCreatedDate().toString());
        dto.setLastModifiedDate(entity.getLastModifiedDate().toString());

        return dto;
    }
}
