package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.HackathonResultDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.HackathonResult;
import com.hacof.hackathon.entity.Team;

public class HackathonResultMapperManual {

    public static HackathonResult toEntity(HackathonResultDTO dto) {
        if (dto == null) return null;

        HackathonResult entity = new HackathonResult();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        if (dto.getHackathonId() != null) {
            Hackathon hackathon = new Hackathon();
            hackathon.setId(Long.parseLong(dto.getHackathonId()));
            entity.setHackathon(hackathon);
        }

        if (dto.getTeamId() != null) {
            Team team = new Team();
            team.setId(Long.parseLong(dto.getTeamId()));
            entity.setTeam(team);
        }

        entity.setTotalScore(dto.getTotalScore());
        entity.setPlacement(dto.getPlacement());
        entity.setAward(dto.getAward());
        entity.setCreatedDate(dto.getCreatedAt());
        entity.setLastModifiedDate(dto.getUpdatedAt());

        return entity;
    }

    public static HackathonResultDTO toDto(HackathonResult entity) {
        if (entity == null) return null;

        HackathonResultDTO dto = new HackathonResultDTO();

        dto.setId(String.valueOf(entity.getId()));

        if (entity.getHackathon() != null) {
            dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        if (entity.getTeam() != null) {
            dto.setTeamId(String.valueOf(entity.getTeam().getId()));
            dto.setTeam(TeamMapperManual.toDto(entity.getTeam()));
        }

        dto.setTotalScore(entity.getTotalScore());
        dto.setPlacement(entity.getPlacement());
        dto.setAward(entity.getAward());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static void updateEntityFromDto(HackathonResultDTO dto, HackathonResult entity) {
        if (dto == null || entity == null) return;

        entity.setTotalScore(dto.getTotalScore());
        entity.setPlacement(dto.getPlacement());
        entity.setAward(dto.getAward());
        entity.setLastModifiedDate(dto.getUpdatedAt());
    }
}
