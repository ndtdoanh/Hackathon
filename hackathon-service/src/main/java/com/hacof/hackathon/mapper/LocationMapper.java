package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO convertToDTO(Location location);

    Location convertToEntity(LocationDTO locationDTO);
}
