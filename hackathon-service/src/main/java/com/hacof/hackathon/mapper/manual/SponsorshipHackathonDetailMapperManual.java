package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.SponsorshipDetailStatus;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

public class SponsorshipHackathonDetailMapperManual {

    public static SponsorshipHackathonDetail toEntity(SponsorshipHackathonDetailDTO dto) {
        if (dto == null) return null;

        SponsorshipHackathonDetail entity = new SponsorshipHackathonDetail();
        if (dto.getId() != null && !dto.getId().isBlank()) {
            entity.setId(Long.parseLong(dto.getId()));
        }
        entity.setMoneySpent(dto.getMoneySpent());
        entity.setContent(dto.getContent());
        entity.setStatus(SponsorshipDetailStatus.valueOf(dto.getStatus()));
        entity.setTimeFrom(dto.getTimeFrom());
        entity.setTimeTo(dto.getTimeTo());

        return entity;
    }

    public static SponsorshipHackathonDetailDTO toDto(SponsorshipHackathonDetail entity) {
        if (entity == null) return null;

        SponsorshipHackathonDetailDTO dto = new SponsorshipHackathonDetailDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setSponsorshipHackathonId(
                entity.getSponsorshipHackathon() != null
                        ? String.valueOf(entity.getSponsorshipHackathon().getId())
                        : null);
        dto.setMoneySpent(entity.getMoneySpent());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus().name());
        dto.setTimeFrom(entity.getTimeFrom());
        dto.setTimeTo(entity.getTimeTo());

        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static void updateEntityFromDto(SponsorshipHackathonDetailDTO dto, SponsorshipHackathonDetail entity) {
        if (dto == null || entity == null) return;

        entity.setMoneySpent(dto.getMoneySpent());
        entity.setContent(dto.getContent());
        entity.setStatus(SponsorshipDetailStatus.valueOf(dto.getStatus()));
        entity.setTimeFrom(dto.getTimeFrom());
        entity.setTimeTo(dto.getTimeTo());
    }
}
