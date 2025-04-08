package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.UserTeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeam;

public class UserTeamMapperManual {

    public static UserTeamDTO toDto(UserTeam entity) {
        if (entity == null) return null;

        UserTeamDTO dto = new UserTeamDTO();
        dto.setId(String.valueOf(entity.getId()));

        if (entity.getUser() != null) {
            dto.setUserId(String.valueOf(entity.getUser().getId()));
            dto.setUser(UserMapperManual.toDto(entity.getUser()));
        }

        if (entity.getTeam() != null) {
            dto.setTeamId(String.valueOf(entity.getTeam().getId()));
        }

        //        dto.setCreatedDate(entity.getCreatedDate());
        //        dto.setCreatedBy(entity.getCreatedBy());
        //        dto.setUpdatedDate(entity.getUpdatedDate());
        //        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

    public static UserTeam toEntity(UserTeamDTO dto) {
        if (dto == null) return null;

        UserTeam entity = new UserTeam();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(Long.parseLong(dto.getUserId()));
            entity.setUser(user);
        }

        if (dto.getTeamId() != null) {
            Team team = new Team();
            team.setId(Long.parseLong(dto.getTeamId()));
            entity.setTeam(team);
        }

        //        entity.setCreatedDate(dto.getCreatedDate());
        //        entity.setCreatedBy(dto.getCreatedBy());
        //        entity.setUpdatedDate(dto.getUpdatedDate());
        //        entity.setUpdatedBy(dto.getUpdatedBy());

        return entity;
    }
}
