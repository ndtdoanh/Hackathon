package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamRound;

public class TeamRoundMapperManual {

    public static TeamRoundDTO toDto(TeamRound entity) {
        if (entity == null) return null;

        TeamRoundDTO dto = new TeamRoundDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setTeamId(entity.getTeam() != null ? String.valueOf(entity.getTeam().getId()) : null);
        dto.setTeam(TeamMapperManual.toDtoWithLeaderAndMembers(entity.getTeam()));
        dto.setRoundId(
                entity.getRound() != null ? String.valueOf(entity.getRound().getId()) : null);
        dto.setRound(RoundMapperManual.toDto(entity.getRound()));
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());


        return dto;
    }

    public static TeamRound toEntity(TeamRoundDTO dto) {
        if (dto == null) return null;

        TeamRound entity = new TeamRound();
        entity.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : 0);

        Team team = new Team();
        team.setId(dto.getTeamId() != null ? Long.parseLong(dto.getTeamId()) : 0);
        entity.setTeam(team);

        Round round = new Round();
        round.setId(dto.getRoundId() != null ? Long.parseLong(dto.getRoundId()) : 0);
        entity.setRound(round);

        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        //        entity.setCreatedByUserName(dto.getCreatedByUserName());
        //        entity.setCreatedAt(dto.getCreatedAt());
        //        entity.setLastModifiedByUserName(dto.getLastModifiedByUserName());
        //        entity.setUpdatedAt(dto.getUpdatedAt());

        // not assign teamRoundJudges here

        return entity;
    }
}
