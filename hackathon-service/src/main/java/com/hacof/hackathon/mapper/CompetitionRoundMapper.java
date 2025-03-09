package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.CompetitionRoundDTO;
import com.hacof.hackathon.entity.CompetitionRound;

@Mapper(componentModel = "spring")
public interface CompetitionRoundMapper {
    CompetitionRoundDTO convertToDTO(CompetitionRound competitionRound);

    CompetitionRound convertToEntity(CompetitionRoundDTO competitionRoundDTO);
}
