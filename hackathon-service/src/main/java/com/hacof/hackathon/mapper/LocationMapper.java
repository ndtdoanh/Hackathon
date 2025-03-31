package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(LocationDTO locationDTO);

    @Mapping(target = "createdByUserName", source = "createdBy", qualifiedByName = "mapUserToString")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "lastModifiedByUserName", source = "lastModifiedBy", qualifiedByName = "mapUserToString")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    LocationDTO toDto(Location location);

    void updateEntityFromDto(LocationDTO locationDTO, @MappingTarget Location location);

    @Named("mapUserToString")
    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
}
