package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;

@Mapper(componentModel = "spring")
public interface RoundMapper {
    @Mapping(source = "hackathon.id", target = "hackathonId")
    RoundDTO toDTO(Round round);

    @Mapping(source = "hackathonId", target = "hackathon.id")
    Round toEntity(RoundDTO roundDTO);

    void updateEntityFromDTO(RoundDTO roundDTO, @MappingTarget Round round);
}
