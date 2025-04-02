package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;

@Mapper(componentModel = "spring")
public interface MentorshipRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(mentorshipRequest.getId()))")
    @Mapping(target = "hackathonId", expression = "java(String.valueOf(mentorshipRequest.getHackathon().getId()))")
    @Mapping(target = "mentorId", expression = "java(String.valueOf(mentorshipRequest.getMentor().getId()))")
    @Mapping(target = "teamId", expression = "java(String.valueOf(mentorshipRequest.getTeam().getId()))")
    @Mapping(target = "evaluatedById", expression = "java(String.valueOf(mentorshipRequest.getEvaluatedBy().getId()))")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(mentorshipRequest.getCreatedBy() != null ? mentorshipRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(mentorshipRequest.getLastModifiedBy() != null ? mentorshipRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    MentorshipRequestDTO toDto(MentorshipRequest mentorshipRequest);

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "mentor.id", source = "mentorId")
    @Mapping(target = "team.id", source = "teamId")
    @Mapping(target = "evaluatedBy.id", source = "evaluatedById")
    MentorshipRequest toEntity(MentorshipRequestDTO mentorshipRequestDTO);

    void updateEntityFromDto(MentorshipRequestDTO dto, @MappingTarget MentorshipRequest entity);
}
