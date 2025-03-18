package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.TeamRequest;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class})
public interface TeamRequestMapper {
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "leader.id", target = "leaderId")
    @Mapping(source = "reviewedBy.id", target = "reviewedBy")
    TeamRequestDTO toDTO(TeamRequest entity);

    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "leader", ignore = true)
    @Mapping(target = "reviewedBy", qualifiedByName = "mapUserFromId")
    @Mapping(target = "reviewedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    TeamRequest toEntity(TeamRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "leader", ignore = true)
    @Mapping(target = "reviewedBy", qualifiedByName = "mapUserFromId")
    @Mapping(target = "reviewedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDTO(TeamRequestDTO dto, @MappingTarget TeamRequest entity);
}
