package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.MentorshipStatus;
import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;

import java.time.LocalDateTime;

public class MentorshipRequestMapperManual {

    public static MentorshipRequest toEntity(MentorshipRequestDTO dto) {
        if (dto == null) return null;

        MentorshipRequest entity = new MentorshipRequest();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        entity.setStatus(MentorshipStatus.valueOf(dto.getStatus()));
        entity.setEvaluatedAt(dto.getEvaluatedAt() != null ? dto.getEvaluatedAt() : LocalDateTime.now());

        return entity;
    }

    public static MentorshipRequestDTO toDto(MentorshipRequest entity) {
        if (entity == null) return null;

        MentorshipRequestDTO dto = new MentorshipRequestDTO();
        dto.setId(entity.getId() != 0 ? String.valueOf(entity.getId()) : null);
        dto.setEvaluatedAt(entity.getEvaluatedAt());

        // Set Hackathon
        if (entity.getHackathon() != null) {
            dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        // Set Mentor
        if (entity.getMentor() != null) {
            dto.setMentorId(String.valueOf(entity.getMentor().getId()));
            dto.setMentor(UserMapperManual.toDto(entity.getMentor()));
        }

        // Set Team
        if (entity.getTeam() != null) {
            dto.setTeamId(String.valueOf(entity.getTeam().getId()));
            dto.setTeam(TeamMapperManual.toDto(entity.getTeam()));
        }

        // Evaluated by
        if (entity.getEvaluatedBy() != null) {
            dto.setEvaluatedById(String.valueOf(entity.getEvaluatedBy().getId()));
            dto.setEvaluatedBy(UserMapperManual.toDto(entity.getEvaluatedBy()));
        }
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().toString() : null);

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }
}
