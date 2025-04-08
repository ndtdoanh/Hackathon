package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.entity.SponsorshipHackathon;

public class SponsorshipHackathonMapperManual {

    public static SponsorshipHackathonDTO toDto(SponsorshipHackathon entity) {
        if (entity == null) return null;

        SponsorshipHackathonDTO dto = new SponsorshipHackathonDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setTotalMoney(entity.getTotalMoney());

        if (entity.getHackathon() != null) {
            dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
            dto.setHackathon(HackathonMapperManual.toDto(entity.getHackathon()));
        }

        if (entity.getSponsorship() != null) {
            dto.setSponsorshipId(String.valueOf(entity.getSponsorship().getId()));
            // Tránh vòng lặp vô hạn: không nên set sponsorship vào đây nếu DTO cũng chứa sponsorshipHackathons
        }

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static SponsorshipHackathon toEntity(SponsorshipHackathonDTO dto) {
        if (dto == null) return null;

        SponsorshipHackathon entity = new SponsorshipHackathon();

        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }

        entity.setTotalMoney(dto.getTotalMoney());

        if (dto.getHackathonId() != null) {
            Hackathon hackathon = new Hackathon();
            hackathon.setId(Long.parseLong(dto.getHackathonId()));
            entity.setHackathon(hackathon);
        }

        if (dto.getSponsorshipId() != null) {
            Sponsorship sponsorship = new Sponsorship();
            sponsorship.setId(Long.parseLong(dto.getSponsorshipId()));
            entity.setSponsorship(sponsorship);
        }

        return entity;
    }
}
