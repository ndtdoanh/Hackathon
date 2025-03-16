package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.MentorRoundDTO;
import com.hacof.hackathon.entity.MentorRound;

@Mapper(componentModel = "spring")
public interface MentorRoundMapper {
    MentorRoundDTO toDTO(MentorRound mentorRound);

    MentorRound toEntity(MentorRoundDTO mentorRoundDTO);
}
