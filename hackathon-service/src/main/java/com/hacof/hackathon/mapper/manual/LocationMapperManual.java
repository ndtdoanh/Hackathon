package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

public class LocationMapperManual {

    public static LocationDTO toDto(Location entity) {
        if (entity == null) return null;

        LocationDTO dto = new LocationDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static Location toEntity(LocationDTO dto) {
        if (dto == null) return null;

        Location entity = new Location();
        entity.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : 0);
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        return entity;
    }
}
