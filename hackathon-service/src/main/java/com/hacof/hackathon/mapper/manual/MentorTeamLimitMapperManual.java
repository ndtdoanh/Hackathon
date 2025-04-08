package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.MentorTeamLimitDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorTeamLimit;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

public class MentorTeamLimitMapperManual {

    public static MentorTeamLimitDTO toDto(MentorTeamLimit entity) {
        if (entity == null) return null;

        MentorTeamLimitDTO dto = new MentorTeamLimitDTO();
        dto.setId(String.valueOf(entity.getId()));

        if (entity.getHackathon() != null) {
            dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        if (entity.getMentor() != null) {
            dto.setMentorId(String.valueOf(entity.getMentor().getId()));
            dto.setMentor(UserMapperManual.toDto(entity.getMentor()));
        }

        if (entity.getTeam() != null) {
            dto.setTeamId(String.valueOf(entity.getTeam().getId()));
            dto.setTeam(TeamMapperManual.toDto(entity.getTeam()));
        }

        dto.setMaxTeams(entity.getMaxTeams());
        dto.setMaxMentors(entity.getMaxMentors());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static MentorTeamLimit toEntity(MentorTeamLimitDTO dto) {
        if (dto == null) return null;

        MentorTeamLimit entity = new MentorTeamLimit();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        if (dto.getHackathonId() != null) {
            Hackathon hackathon = new Hackathon();
            hackathon.setId(Long.parseLong(dto.getHackathonId()));
            entity.setHackathon(hackathon);
        }

        if (dto.getMentorId() != null) {
            User mentor = new User();
            mentor.setId(Long.parseLong(dto.getMentorId()));
            entity.setMentor(mentor);
        }

        if (dto.getTeamId() != null) {
            Team team = new Team();
            team.setId(Long.parseLong(dto.getTeamId()));
            entity.setTeam(team);
        }

        entity.setMaxTeams(dto.getMaxTeams());
        entity.setMaxMentors(dto.getMaxMentors());

        return entity;
    }
}
