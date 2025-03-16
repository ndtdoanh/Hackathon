package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;

@Mapper(componentModel = "spring")
public interface RoundMapper {
    RoundDTO convertToDTO(Round round);

    Round convertToEntity(RoundDTO roundDTO);
}
