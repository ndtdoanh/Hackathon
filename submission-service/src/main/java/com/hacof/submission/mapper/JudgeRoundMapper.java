package com.hacof.submission.mapper;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.entity.JudgeRound;
import org.springframework.stereotype.Component;

@Component
public class JudgeRoundMapper {

//    // Chuyển từ DTO sang Entity
//    public JudgeRound toEntity(JudgeRoundRequestDTO dto) {
//        JudgeRound judgeRound = new JudgeRound();
//        judgeRound.setJudgeId(dto.getJudgeId());
//        judgeRound.setRoundId(dto.getRoundId());
//        return judgeRound;
//    }
//
//    // Chuyển từ Entity sang DTO
//    public JudgeRoundResponseDTO toResponseDTO(JudgeRound entity) {
//        JudgeRoundResponseDTO dto = new JudgeRoundResponseDTO();
//        dto.setId(entity.getId());
//        dto.setJudgeId(entity.getJudge().getId());
//        dto.setRoundId(entity.getRound().getId());
//        dto.setIsDeleted(entity.isIsDeleted());
//        dto.setCreatedDate(entity.getCreatedDate().toString());
//        dto.setLastModifiedDate(entity.getLastModifiedDate().toString());
//        return dto;
//    }
}
