package com.hacof.hackathon.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.User;

// @Mapper(
//        componentModel = "spring",
//        uses = {HackathonMapper.class})
@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mapping(target = "roundLocations", ignore = true)
    @Mapping(source = "hackathon", target = "hackathon", qualifiedByName = "mapHackathonToHackathonDTO")
    RoundDTO toDto(Round round);

    @Mapping(target = "hackathon", ignore = true)
    Round toEntity(RoundDTO dto);

    @Named("mapHackathonToHackathonDTO")
    default HackathonDTO mapHackathonToHackathonDTO(Hackathon hackathon) {
        HackathonDTO dto = new HackathonDTO();
        dto.setId(String.valueOf(hackathon.getId()));
        dto.setRoundIds(hackathon.getRounds().stream().map(Round::getId).collect(Collectors.toList()));
        dto.setCreatedBy(mapUserToString(hackathon.getCreatedBy()));
        return dto;
    }

    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
}
