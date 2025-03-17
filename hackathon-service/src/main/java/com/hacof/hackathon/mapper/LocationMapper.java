package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {
    @Mapping(target = "roundLocations", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    LocationDTO toDTO(Location location);

    @Mapping(target = "roundLocations", ignore = true)
    void updateEntityFromDTO(LocationDTO locationDTO, @MappingTarget Location location);
}
