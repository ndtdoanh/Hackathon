package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamHackathonDTO;
import com.hacof.hackathon.entity.TeamHackathon;

public class TeamHackathonMapperManual {

    public static TeamHackathonDTO toDto(TeamHackathon entity) {
        if (entity == null) return null;

        TeamHackathonDTO dto = new TeamHackathonDTO();
        dto.setId(String.valueOf(entity.getId()));
//        dto.setStatus(entity.getStatus());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setLastModifiedByUserName(entity.getLastModifiedByUserName());
//        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getHackathon() != null) {
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        return dto;
    }

    public static TeamHackathon toEntity(TeamHackathonDTO dto) {
        if (dto == null) return null;

        TeamHackathon entity = new TeamHackathon();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

//        entity.setStatus(dto.getStatus());
//        entity.setCreatedAt(dto.getCreatedAt());
//        entity.setLastModifiedByUserName(dto.getLastModifiedByUserName());
//        entity.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getHackathon() != null) {
            entity.setHackathon(HackathonMapperManual.toEntity(dto.getHackathon()));
        }

        return entity;
    }
}