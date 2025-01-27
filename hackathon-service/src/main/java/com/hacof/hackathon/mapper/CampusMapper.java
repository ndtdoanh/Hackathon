package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.CampusDTO;
import com.hacof.hackathon.entity.Campus;

@Mapper(componentModel = "spring")
public interface CampusMapper {
    CampusDTO convertToDTO(Campus campus);

    Campus convertToEntity(CampusDTO campusDTO);
}
