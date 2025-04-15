package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.service.LocationService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {LocationMapper.class})
public interface RoundLocationMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(roundLocation.getId()))")
    @Mapping(
            target = "roundId",
            expression =
                    "java(roundLocation.getRound() != null ? String.valueOf(roundLocation.getRound().getId()) : null)")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(roundLocation.getCreatedBy() != null ? roundLocation.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(roundLocation.getLastModifiedBy() != null ? roundLocation.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    RoundLocationDTO toDto(RoundLocation roundLocation);

    @Mapping(target = "location", ignore = true)
    @Mapping(target = "round", ignore = true)
    RoundLocation toEntity(RoundLocationDTO dto, @Context LocationService locationService);

    @AfterMapping
    default void mapLocation(
            RoundLocationDTO dto, @MappingTarget RoundLocation entity, @Context LocationService locationService) {
        if (dto.getLocationId() != null) {
            entity.setLocation(locationService.getLocationEntityById(Long.parseLong(dto.getLocationId())));
        }
    }
}
