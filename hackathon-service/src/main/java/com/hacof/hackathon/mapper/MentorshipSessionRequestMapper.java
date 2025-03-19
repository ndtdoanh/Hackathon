package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorshipSessionRequest;

@Mapper(componentModel = "spring")
public interface MentorshipSessionRequestMapper {
    MentorshipSessionRequestDTO toDTO(MentorshipSessionRequest mentorshipSessionRequest);

    MentorshipSessionRequest toEntity(MentorshipSessionRequestDTO mentorshipSessionRequestDTO);
}
