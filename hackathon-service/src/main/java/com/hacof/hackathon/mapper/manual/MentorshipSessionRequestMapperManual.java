package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.entity.MentorshipSessionRequest;
import com.hacof.hackathon.entity.User;

public class MentorshipSessionRequestMapperManual {

    public static MentorshipSessionRequest toEntity(MentorshipSessionRequestDTO dto) {
        if (dto == null) return null;

        MentorshipSessionRequest entity = new MentorshipSessionRequest();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setLocation(dto.getLocation());
        entity.setDescription(dto.getDescription());
        entity.setStatus(Status.valueOf(dto.getStatus()));
        entity.setEvaluatedAt(dto.getEvaluatedAt());

        // Mapping evaluatedBy (user)
        if (dto.getEvaluatedById() != null) {
            User evaluatedBy = new User();
            evaluatedBy.setId(Long.parseLong(dto.getEvaluatedById()));
            entity.setEvaluatedBy(evaluatedBy);
        }

        // Mapping mentorTeam
        if (dto.getMentorTeamId() != null) {
            MentorTeam mentorTeam = new MentorTeam();
            mentorTeam.setId(Long.parseLong(dto.getMentorTeamId()));
            entity.setMentorTeam(mentorTeam);
        }

        return entity;
    }

    public static MentorshipSessionRequestDTO toDto(MentorshipSessionRequest entity) {
        if (entity == null) return null;

        MentorshipSessionRequestDTO dto = new MentorshipSessionRequestDTO();

        dto.setId(entity.getId() != 0 ? String.valueOf(entity.getId()) : null);
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().toString() : null);
        dto.setEvaluatedAt(entity.getEvaluatedAt());

        // Mapping evaluatedBy
        if (entity.getEvaluatedBy() != null) {
            dto.setEvaluatedById(String.valueOf(entity.getEvaluatedBy().getId()));
            dto.setEvaluatedBy(UserMapperManual.toDto(entity.getEvaluatedBy()));
        }

        // Mapping mentorTeam
        if (entity.getMentorTeam() != null) {
            dto.setMentorTeamId(String.valueOf(entity.getMentorTeam().getId()));
            dto.setMentorTeam(MentorTeamMapperManual.toDto(entity.getMentorTeam()));
        }

        // Audit
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static void updateEntityFromDto(MentorshipSessionRequestDTO dto, MentorshipSessionRequest entity) {
        if (dto == null || entity == null) return;

        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setLocation(dto.getLocation());
        entity.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            entity.setStatus(Status.valueOf(dto.getStatus()));
        }
        entity.setEvaluatedAt(dto.getEvaluatedAt());
    }
}
