package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.TeamRequest;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(teamRequest.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(teamRequest.getLastModifiedBy() != null ? teamRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "hackathon", target = "hackathon")
    TeamRequestDTO toDto(TeamRequest teamRequest);

    @Mapping(source = "hackathonId", target = "hackathon.id")
    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    void updateEntityFromDto(TeamRequestDTO teamRequestDTO, @MappingTarget TeamRequest teamRequest);
}
