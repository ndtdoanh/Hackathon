package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    HackathonDTO convertToDTO(Hackathon hackathon);

    Hackathon convertToEntity(HackathonDTO hackathonDTO);
}
