package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.AwardDTO;
import com.hacof.hackathon.entity.Award;

@Mapper(componentModel = "spring")
public interface AwardMapper {
    AwardDTO convertToDTO(Award award);

    Award convertToEntity(AwardDTO awardDTO);
}
