package com.hacof.hackathon.mapper.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.service.LocationService;

public class RoundMapperManual {

    public static Round toEntity(RoundDTO dto, LocationService locationService) {
        Round entity = new Round();
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setRoundNumber(dto.getRoundNumber());
        entity.setRoundTitle(dto.getRoundTitle());
        entity.setStatus(RoundStatus.valueOf(dto.getStatus()));

        if (dto.getHackathonId() != null) {
            Hackathon hackathon = new Hackathon();
            hackathon.setId(Long.parseLong(dto.getHackathonId()));
            entity.setHackathon(hackathon);
        }

        if (dto.getRoundLocations() != null) {
            List<RoundLocation> locations = dto.getRoundLocations().stream()
                    .map(locDto -> {
                        RoundLocation loc = RoundLocationMapperManual.toEntity(locDto, locationService);
                        loc.setRound(entity); // gắn vòng ngược lại
                        return loc;
                    })
                    .collect(Collectors.toList());
            entity.setRoundLocations(locations);
        } else {
            entity.setRoundLocations(new ArrayList<>());
        }

        return entity;
    }

    public static RoundDTO toDto(Round entity) {
        RoundDTO dto = new RoundDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setHackathonId(entity.getHackathon() != null ? String.valueOf(entity.getHackathon().getId()) : null);
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setRoundNumber(entity.getRoundNumber());
        dto.setRoundTitle(entity.getRoundTitle());
        dto.setStatus(entity.getStatus().name());

        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());
        dto.setCreatedByUserName(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);

        if (entity.getRoundLocations() != null) {
            List<RoundLocationDTO> locationDTOs = entity.getRoundLocations().stream()
                    .map(RoundLocationMapperManual::toDto)
                    .collect(Collectors.toList());
            dto.setRoundLocations(locationDTOs);
        } else {
            dto.setRoundLocations(new ArrayList<>());
        }

        return dto;
    }
}