package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;

import java.util.stream.Collectors;

public class RoundMapperManual {

    public static RoundDTO toDto(Round entity) {
        if (entity == null) return null;

        RoundDTO dto = new RoundDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setHackathonId(
                entity.getHackathon() != null
                        ? String.valueOf(entity.getHackathon().getId())
                        : null);
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setRoundNumber(entity.getRoundNumber());
        dto.setRoundTitle(entity.getRoundTitle());
        dto.setStatus(entity.getStatus().name());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        if (entity.getRoundLocations() != null) {
            dto.setRoundLocations(entity.getRoundLocations().stream()
                    .map(RoundLocationMapperManual::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Round toEntity(RoundDTO dto) {
        if (dto == null) return null;

        Round entity = new Round();
        entity.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : 0);

        Hackathon hackathon = new Hackathon();
        hackathon.setId(dto.getHackathonId() != null ? Long.parseLong(dto.getHackathonId()) : 0);
        entity.setHackathon(hackathon);

        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setRoundNumber(dto.getRoundNumber());
        entity.setRoundTitle(dto.getRoundTitle());
        entity.setStatus(RoundStatus.valueOf(dto.getStatus()));

        //        entity.setCreatedByUserName(dto.getCreatedByUserName());
        //        entity.setCreatedAt(dto.getCreatedAt());
        //        entity.setLastModifiedByUserName(dto.getLastModifiedByUserName());
        //        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }
}
