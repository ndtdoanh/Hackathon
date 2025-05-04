package com.hacof.hackathon.mapper.manual;

import java.util.Set;
import java.util.stream.Collectors;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.UserTeamDTO;
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
            leader.setAvatarUrl(
                    dto.getTeamLeader() != null ? dto.getTeamLeader().getAvatarUrl() : null);
            leader.setBio(dto.getTeamLeader() != null ? dto.getTeamLeader().getBio() : null);
            entity.setTeamLeader(leader);
        }

        return entity;
    }

    public static TeamDTO toDto(Team entity) {
        if (entity == null) return null;

        TeamDTO dto = new TeamDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setName(entity.getName());

        if (entity.getTeamLeader() != null) {
            dto.setTeamLeaderId(String.valueOf(entity.getTeamLeader().getId()));
            dto.setTeamLeader(UserMapperManual.toDto(entity.getTeamLeader()));
        }

        if (entity.getTeamMembers() != null && !entity.getTeamMembers().isEmpty()) {
            dto.setTeamMembers(entity.getTeamMembers().stream()
                    .map(UserTeamMapperManual::toDto)
                    .collect(Collectors.toSet()));
        }

        if (entity.getTeamHackathons() != null && !entity.getTeamHackathons().isEmpty()) {
            dto.setTeamHackathons(entity.getTeamHackathons().stream()
                    .map(TeamHackathonMapperManual::toDto)
                    .collect(Collectors.toList()));
        }

        // Map audit fields
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setUpdatedAt(entity.getLastModifiedDate());

        dto.setDeleted(entity.getIsDeleted());
        dto.setDeletedById(
                entity.getDeletedBy() != null
                        ? String.valueOf(entity.getDeletedBy().getId())
                        : null);
        return dto;
    }

    public static TeamDTO toDtoWithLeaderAndMembers(Team team) {
        if (team == null) return null;

        TeamDTO dto = new TeamDTO();
        dto.setId(String.valueOf(team.getId()));
        dto.setName(team.getName());

        if (team.getTeamLeader() != null) {
            dto.setTeamLeader(UserMapperManual.toDto(team.getTeamLeader()));
            dto.setTeamLeaderId(String.valueOf(team.getTeamLeader().getId()));
        }

        if (team.getTeamMembers() != null) {
            Set<UserTeamDTO> members = team.getTeamMembers().stream()
                    .map(UserTeamMapperManual::toDto)
                    .collect(Collectors.toSet());
            dto.setTeamMembers(members);
        }

        return dto;
    }
}
