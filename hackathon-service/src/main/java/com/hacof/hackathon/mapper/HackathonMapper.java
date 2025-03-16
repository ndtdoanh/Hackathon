package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.User;

@Mapper(
        componentModel = "spring",
        uses = {RoundMapper.class})
public interface HackathonMapper {
    @Mapping(target = "rounds", source = "rounds")
    HackathonDTO convertToDTO(Hackathon hackathon);

    Hackathon convertToEntity(HackathonDTO hackathonDTO);

    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
