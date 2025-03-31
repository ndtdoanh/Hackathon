package com.hacof.hackathon.mapper;

import java.util.ArrayList;

import org.mapstruct.*;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.entity.User;

@Mapper(
        componentModel = "spring",
        uses = {TeamMapper.class, RoundMapper.class})
public interface TeamRoundMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapIdToString")
    @Mapping(source = "team.id", target = "teamId", qualifiedByName = "mapIdToString")
    @Mapping(source = "round.id", target = "roundId", qualifiedByName = "mapIdToString")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    //    @Mapping(source = "createdDate", target = "createdAt")
    //    @Mapping(source = "createdBy", target = "createdByUserName", qualifiedByName = "mapUserToUsername")
    //    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    //    @Mapping(source = "lastModifiedBy", target = "lastModifiedByUserName", qualifiedByName = "mapUserToUsername")
    TeamRoundDTO toDto(TeamRound teamRound);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "round", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "teamRoundJudges", ignore = true)
    //    @Mapping(target = "createdBy", ignore = true)
    //    @Mapping(target = "createdDate", ignore = true)
    //    @Mapping(target = "lastModifiedBy", ignore = true)
    //    @Mapping(target = "lastModifiedDate", ignore = true)
    TeamRound toEntity(TeamRoundDTO teamRoundDTO);

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
            throw new IllegalArgumentException("ID không hợp lệ: " + id);
        }
    }

    @Named("mapUserToUsername")
    default String mapUserToUsername(User user) {
        return user != null ? user.getUsername() : null;
    }

    @AfterMapping
    default void handleNullValues(@MappingTarget TeamRound teamRound) {
        if (teamRound.getTeamRoundJudges() == null) {
            teamRound.setTeamRoundJudges(new ArrayList<>());
        }
    }
}
