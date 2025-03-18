package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.JudgeRoundDTO;
import com.hacof.hackathon.entity.JudgeRound;

@Mapper(componentModel = "spring")
public interface JudgeRoundMapper {
    JudgeRoundDTO toDTO(JudgeRound judgeRound);

    JudgeRound toEntity(JudgeRoundDTO judgeRoundDTO);
}
