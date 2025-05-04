package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamHackathonDTO;
import com.hacof.hackathon.entity.TeamHackathon;

public class TeamHackathonMapperManual {

    public static TeamHackathonDTO toDto(TeamHackathon entity) {
        if (entity == null) return null;

        TeamHackathonDTO dto = new TeamHackathonDTO();
        dto.setId(String.valueOf(entity.getId()));

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

        if (dto.getHackathon() != null) {
            entity.setHackathon(HackathonMapperManual.toEntity(dto.getHackathon()));
        }

        return entity;
    }
}
