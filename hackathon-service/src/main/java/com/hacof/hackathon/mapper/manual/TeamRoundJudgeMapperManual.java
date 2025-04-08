package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamRoundJudgeDTO;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.entity.TeamRoundJudge;
import com.hacof.hackathon.entity.User;

public class TeamRoundJudgeMapperManual {

    public static TeamRoundJudgeDTO toDto(TeamRoundJudge entity) {
        if (entity == null) return null;

        TeamRoundJudgeDTO dto = new TeamRoundJudgeDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setTeamRoundId(
                entity.getTeamRound() != null
                        ? String.valueOf(entity.getTeamRound().getId())
                        : null);
        dto.setTeamRound(TeamRoundMapperManual.toDto(entity.getTeamRound()));
        dto.setJudgeId(
                entity.getJudge() != null ? String.valueOf(entity.getJudge().getId()) : null);
        dto.setJudge(UserMapperManual.toDto(entity.getJudge()));
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
//        dto.setLastModifiedByUserName(
//                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        //dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static TeamRoundJudge toEntity(TeamRoundJudgeDTO dto) {
        if (dto == null) return null;

        TeamRoundJudge entity = new TeamRoundJudge();
        entity.setId(dto.getId() != null ? Long.parseLong(dto.getId()) : 0);

        TeamRound teamRound = new TeamRound();
        teamRound.setId(dto.getTeamRoundId() != null ? Long.parseLong(dto.getTeamRoundId()) : 0);
        entity.setTeamRound(teamRound);

        User judge = new User();
        judge.setId(dto.getJudgeId() != null ? Long.parseLong(dto.getJudgeId()) : 0);
        entity.setJudge(judge);

        //    entity.setCreatedByUserName(dto.getCreatedByUserName());
        //    entity.setCreatedAt(dto.getCreatedAt());
        //    entity.setLastModifiedByUserName(dto.getLastModifiedByUserName());
        //    entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }
}
