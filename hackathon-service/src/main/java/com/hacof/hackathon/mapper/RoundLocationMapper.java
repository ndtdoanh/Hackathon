package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.RoundLocation;

@Mapper(componentModel = "spring")
public interface RoundLocationMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(roundLocation.getId()))")
    @Mapping(
            target = "roundId",
            expression =
                    "java(roundLocation.getRound() != null ? String.valueOf(roundLocation.getRound().getId()) : null)")
    @Mapping(
            target = "locationId",
            expression =
                    "java(roundLocation.getLocation() != null ? String.valueOf(roundLocation.getLocation().getId()) : null)")
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

    RoundLocation toEntity(RoundLocationDTO roundLocationDTO);

    void updateEntityFromDto(RoundLocationDTO roundLocationDTO, @MappingTarget RoundLocation roundLocation);
}
