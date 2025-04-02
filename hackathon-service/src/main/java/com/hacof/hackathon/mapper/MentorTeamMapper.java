package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.entity.MentorTeam;

@Mapper(componentModel = "spring")
public interface MentorTeamMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(mentorTeam.getId()))")
    @Mapping(target = "hackathonId", expression = "java(String.valueOf(mentorTeam.getHackathon().getId()))")
    @Mapping(target = "mentorId", expression = "java(String.valueOf(mentorTeam.getMentor().getId()))")
    @Mapping(target = "teamId", expression = "java(String.valueOf(mentorTeam.getTeam().getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(mentorTeam.getCreatedBy() != null ? mentorTeam.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(mentorTeam.getLastModifiedBy() != null ? mentorTeam.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    MentorTeamDTO toDto(MentorTeam mentorTeam);

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "mentor.id", source = "mentorId")
    @Mapping(target = "team.id", source = "teamId")
    MentorTeam toEntity(MentorTeamDTO mentorTeamDTO);

    void updateEntityFromDto(MentorTeamDTO mentorTeamDTO, @MappingTarget MentorTeam mentorTeam);
}
