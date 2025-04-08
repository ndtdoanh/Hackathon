package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.mapper.LocationMapper;
import com.hacof.hackathon.service.LocationService;

public class RoundLocationMapperManual {

    public static RoundLocationDTO toDto(RoundLocation entity, LocationMapper locationMapper) {
        RoundLocationDTO dto = new RoundLocationDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setLocationId(
                entity.getLocation() != null
                        ? String.valueOf(entity.getLocation().getId())
                        : null);
        dto.setType(entity.getType());

        return dto;
    }

    public static RoundLocation toEntity(RoundLocationDTO dto, LocationService locationService) {
        RoundLocation entity = new RoundLocation();

        Location location = locationService.getLocationEntityById(Long.parseLong(dto.getLocationId()));
        entity.setLocation(location);
        entity.setType(dto.getType());

        return entity;
    }
}
