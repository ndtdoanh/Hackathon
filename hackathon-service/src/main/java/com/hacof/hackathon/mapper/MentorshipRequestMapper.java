package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;

@Mapper(componentModel = "spring")
public interface MentorshipRequestMapper {
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "mentor.id", target = "mentorId")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "evaluatedBy.id", target = "evaluatedById")
    MentorshipRequestDTO toDto(MentorshipRequest mentorshipRequest);

    @Mapping(source = "hackathonId", target = "hackathon.id")
    @Mapping(source = "mentorId", target = "mentor.id")
    @Mapping(source = "teamId", target = "team.id")
    @Mapping(source = "evaluatedById", target = "evaluatedBy.id")
    MentorshipRequest toEntity(MentorshipRequestDTO mentorshipRequestDTO);
}
