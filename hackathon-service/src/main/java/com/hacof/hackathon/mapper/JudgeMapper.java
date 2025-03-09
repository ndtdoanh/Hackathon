package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.JudgeDTO;
import com.hacof.hackathon.entity.Judge;

@Mapper(componentModel = "spring")
public interface JudgeMapper {
    JudgeDTO convertToDTO(Judge judge);

    Judge convertToEntity(JudgeDTO judgeDTO);
}
