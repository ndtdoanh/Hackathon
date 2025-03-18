package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;

@Mapper(componentModel = "spring")
public interface MentorshipRequestMapper {
    MentorshipRequestDTO toDTO(MentorshipRequest mentorshipRequest);

    MentorshipRequest toEntity(MentorshipRequestDTO mentorshipRequestDTO);
}
