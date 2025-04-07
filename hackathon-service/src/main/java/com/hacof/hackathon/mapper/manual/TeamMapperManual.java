package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

public class TeamMapperManual {

    public static Team toEntity(TeamDTO dto) {
        if (dto == null) return null;

        Team entity = new Team();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        entity.setName(dto.getName());
        entity.setBio(dto.getBio());
        entity.setDeleted(dto.isDeleted());

        if (dto.getDeletedById() != null) {
            entity.setDeletedById(Long.parseLong(dto.getDeletedById()));
        }

        // Set team leader
        if (dto.getTeamLeaderId() != null) {
            User leader = new User();
            leader.setId(Long.parseLong(dto.getTeamLeaderId()));
            entity.setTeamLeader(leader);
        }


        return entity;
    }

    public static TeamDTO toDto(Team entity) {
        if (entity == null) return null;

        TeamDTO dto = new TeamDTO();

        dto.setId(String.valueOf(entity.getId()));
        dto.setName(entity.getName());
        dto.setBio(entity.getBio());
        dto.setDeleted(entity.getIsDeleted() != null && entity.getIsDeleted());

        if (entity.getTeamLeader() != null) {
            dto.setTeamLeaderId(String.valueOf(entity.getTeamLeader().getId()));
            dto.setTeamLeader(UserMapperManual.toDto(entity.getTeamLeader()));
        }

        if (entity.getDeletedBy() != null) {
            dto.setDeletedBy(UserMapperManual.toDto(entity.getDeletedBy()));
            dto.setDeletedById(String.valueOf(entity.getDeletedBy().getId()));
        }

        return dto;
    }
}