package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorshipSessionRequest;

@Mapper(componentModel = "spring")
public interface MentorshipSessionRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(mentorshipSessionRequest.getId()))")
    @Mapping(
            target = "mentorTeamId",
            expression = "java(String.valueOf(mentorshipSessionRequest.getMentorTeam().getId()))")
    @Mapping(
            target = "evaluatedById",
            expression =
                    "java(mentorshipSessionRequest.getEvaluatedBy() != null ? String.valueOf(mentorshipSessionRequest.getEvaluatedBy().getId()) : null)")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(mentorshipSessionRequest.getCreatedBy() != null ? mentorshipSessionRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(mentorshipSessionRequest.getLastModifiedBy() != null ? mentorshipSessionRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    MentorshipSessionRequestDTO toDto(MentorshipSessionRequest mentorshipSessionRequest);

    @Mapping(target = "mentorTeam.id", source = "mentorTeamId")
    @Mapping(target = "evaluatedBy.id", source = "evaluatedById")
    MentorshipSessionRequest toEntity(MentorshipSessionRequestDTO mentorshipSessionRequestDTO);

    void updateEntityFromDto(
            MentorshipSessionRequestDTO mentorshipSessionRequestDTO,
            @MappingTarget MentorshipSessionRequest mentorshipSessionRequest);
}
