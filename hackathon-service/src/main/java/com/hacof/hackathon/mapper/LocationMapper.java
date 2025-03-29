package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.User;

// @Mapper(
//        componentModel = "spring",
//        uses = {RoundMapper.class})
@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "roundLocations", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    @Mapping(target = "roundLocations", ignore = true)
    LocationDTO toDto(Location location);

    void updateEntityFromDto(LocationDTO locationDTO, @MappingTarget Location location);

    @Named("mapUserToString")
    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
}
