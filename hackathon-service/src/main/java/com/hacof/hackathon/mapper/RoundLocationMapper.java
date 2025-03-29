package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.entity.User;

@Mapper(
        componentModel = "spring",
        uses = {LocationMapper.class, RoundMapper.class})
public interface RoundLocationMapper {
    RoundLocationDTO toDto(RoundLocation roundLocation);

    RoundLocation toEntity(RoundLocationDTO roundLocationDTO);

    void updateEntityFromDto(RoundLocationDTO roundLocationDTO, @MappingTarget RoundLocation roundLocation);

    @Named("mapUserToString")
    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
}
