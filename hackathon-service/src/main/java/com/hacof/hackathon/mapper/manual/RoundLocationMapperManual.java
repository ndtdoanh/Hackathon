package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.RoundLocation;

public class RoundLocationMapperManual {

    public static RoundLocationDTO toDto(RoundLocation entity) {
        if (entity == null) return null;

        RoundLocationDTO dto = new RoundLocationDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setRoundId(
                entity.getRound() != null ? String.valueOf(entity.getRound().getId()) : null);
        dto.setLocationId(
                entity.getLocation() != null
                        ? String.valueOf(entity.getLocation().getId())
                        : null);
        dto.setLocation(LocationMapperManual.toDto(entity.getLocation()));
        dto.setType(entity.getType());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static RoundLocation toEntity(RoundLocationDTO dto) {
        if (dto == null) return null;

        RoundLocation entity = new RoundLocation();
        entity.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : 0);

        Round round = new Round();
        round.setId(dto.getRoundId() != null ? Long.parseLong(dto.getRoundId()) : 0);
        entity.setRound(round);

        Location location = new Location();
        location.setId(dto.getLocationId() != null ? Long.parseLong(dto.getLocationId()) : 0);
        entity.setLocation(location);

        entity.setType(dto.getType());
        return entity;
    }
}
