package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(LocationDTO locationDTO);

    @Mapping(
            target = "createdByUserName",
            expression = "java(location.getCreatedBy() != null ? location.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(location.getLastModifiedBy() != null ? location.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    LocationDTO toDto(Location location);
}
