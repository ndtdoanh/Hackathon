package com.hacof.hackathon.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.HackathonResultDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.HackathonResult;
import com.hacof.hackathon.entity.Team;

@Mapper(
        componentModel = "spring",
        uses = {HackathonMapper.class, TeamMapper.class})
public interface HackathonResultMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapIdToString")
    @Mapping(source = "hackathon.id", target = "hackathonId", qualifiedByName = "mapIdToString")
    @Mapping(source = "team.id", target = "teamId", qualifiedByName = "mapIdToString")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(hackathonResult.getCreatedBy() != null ? hackathonResult.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(hackathonResult.getLastModifiedBy() != null ? hackathonResult.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    HackathonResultDTO toDto(HackathonResult hackathonResult);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "team", ignore = true)
    HackathonResult toEntity(HackathonResultDTO hackathonResultDTO);

    @Named("mapIdToString")
    default String mapIdToString(Long id) {
        return id != null ? String.valueOf(id) : null;
    }

    @Named("mapStringToId")
    default Long mapStringToId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
    }

    @AfterMapping
    default void updateEntityFromDto(HackathonResultDTO dto, @MappingTarget HackathonResult entity) {
        if (dto.getHackathonId() != null) {
            entity.setHackathon(
                    Hackathon.builder().id(Long.parseLong(dto.getHackathonId())).build());
        }
        if (dto.getTeamId() != null) {
            entity.setTeam(Team.builder().id(Long.parseLong(dto.getTeamId())).build());
        }
    }
}
