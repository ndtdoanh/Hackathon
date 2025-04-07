package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.service.LocationService;

public class RoundLocationMapperManual {
    // convert dto to entity
    public static RoundLocation toEntity(RoundLocationDTO dto, LocationService locationService) {
        RoundLocation entity = new RoundLocation();
        if (dto.getLocationId() != null) {
            Location location = locationService.getLocationEntityById(Long.parseLong(dto.getLocationId()));
            entity.setLocation(location);
        }
        entity.setType(dto.getType());
        return entity;
    }

    // convert entity to dto
    public static RoundLocationDTO toDto(RoundLocation entity) {
        RoundLocationDTO dto = new RoundLocationDTO();
        dto.setLocationId(
                entity.getLocation() != null
                        ? String.valueOf(entity.getLocation().getId())
                        : null);

        if (entity.getLocation() != null) {
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setId(String.valueOf(entity.getLocation().getId()));
            locationDTO.setName(entity.getLocation().getName());
            locationDTO.setAddress(entity.getLocation().getAddress());
            locationDTO.setLongitude(entity.getLocation().getLongitude());
            locationDTO.setLatitude(entity.getLocation().getLatitude());
            locationDTO.setCreatedAt(entity.getLocation().getCreatedDate());
            locationDTO.setUpdatedAt(entity.getLocation().getLastModifiedDate());
            locationDTO.setCreatedByUserName(
                    entity.getLocation().getCreatedBy() != null
                            ? entity.getLocation().getCreatedBy().getUsername()
                            : null);
            locationDTO.setLastModifiedByUserName(
                    entity.getLocation().getLastModifiedBy() != null
                            ? entity.getLocation().getLastModifiedBy().getUsername()
                            : null);
            dto.setLocation(locationDTO);
        }

        dto.setType(entity.getType());

        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());
        dto.setId(String.valueOf(entity.getId()));
        dto.setRoundId(
                entity.getRound() != null ? String.valueOf(entity.getRound().getId()) : null);
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);

        return dto;
    }
}
