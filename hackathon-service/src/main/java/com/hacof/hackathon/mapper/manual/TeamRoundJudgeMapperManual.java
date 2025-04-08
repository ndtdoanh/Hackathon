package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamRoundJudgeDTO;
import com.hacof.hackathon.entity.TeamRoundJudge;

public class TeamRoundJudgeMapperManual {

    public static TeamRoundJudgeDTO toDto(TeamRoundJudge entity) {
        if (entity == null) return null;

        TeamRoundJudgeDTO dto = new TeamRoundJudgeDTO();
        dto.setId(Long.toString(entity.getId()));
        dto.setTeamRoundId(
                entity.getTeamRound() != null
                        ? Long.toString(entity.getTeamRound().getId())
                        : null);
        dto.setJudgeId(
                entity.getJudge() != null ? Long.toString(entity.getJudge().getId()) : null);

        dto.setTeamRound(TeamRoundMapperManual.toDto(entity.getTeamRound()));
        dto.setJudge(UserMapperManual.toDto(entity.getJudge()));

        return dto;
    }

    public static TeamRoundJudge toEntity(TeamRoundJudgeDTO dto) {
        if (dto == null) return null;

        TeamRoundJudge entity = new TeamRoundJudge();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        //        entity.setCreatedByUserName(dto.getCreatedByUserName());
        //        entity.setCreatedAt(dto.getCreatedAt());
        //        entity.setLastModifiedByUserName(dto.getLastModifiedByUserName());
        //        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }
}
