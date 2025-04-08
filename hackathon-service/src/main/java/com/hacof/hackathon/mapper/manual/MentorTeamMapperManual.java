package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.MentorTeamDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

public class MentorTeamMapperManual {

    public static MentorTeam toEntity(MentorTeamDTO dto) {
        if (dto == null) return null;

        MentorTeam entity = new MentorTeam();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        // Hackathon
        if (dto.getHackathonId() != null) {
            Hackathon hackathon = new Hackathon();
            hackathon.setId(Long.parseLong(dto.getHackathonId()));
            entity.setHackathon(hackathon);
        }

        // Mentor (User)
        if (dto.getMentorId() != null) {
            User mentor = new User();
            mentor.setId(Long.parseLong(dto.getMentorId()));
            entity.setMentor(mentor);
        }

        // Team
        if (dto.getTeamId() != null) {
            Team team = new Team();
            team.setId(Long.parseLong(dto.getTeamId()));
            entity.setTeam(team);
        }

        return entity;
    }

    public static MentorTeamDTO toDto(MentorTeam entity) {
        if (entity == null) return null;

        MentorTeamDTO dto = new MentorTeamDTO();

        dto.setId(String.valueOf(entity.getId()));

        // Hackathon
        if (entity.getHackathon() != null) {
            dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        // Mentor (User)
        if (entity.getMentor() != null) {
            dto.setMentorId(String.valueOf(entity.getMentor().getId()));
            dto.setMentor(UserMapperManual.toDto(entity.getMentor()));
        }

        // Team
        if (entity.getTeam() != null) {
            dto.setTeamId(String.valueOf(entity.getTeam().getId()));
            dto.setTeam(TeamMapperManual.toDto(entity.getTeam()));
        }

        // Audit
        dto.setCreatedByUserName(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }
}