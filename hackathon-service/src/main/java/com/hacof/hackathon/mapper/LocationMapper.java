package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO convertToDTO(Location location);
    Location convertToEntity(LocationDTO locationDTO);
}