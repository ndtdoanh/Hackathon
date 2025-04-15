package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.SponsorshipStatus;
import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;

import java.util.stream.Collectors;

public class SponsorshipMapperManual {

    public static SponsorshipDTO toDto(Sponsorship sponsorship) {
        if (sponsorship == null) return null;

        SponsorshipDTO dto = new SponsorshipDTO();
        dto.setId(String.valueOf(sponsorship.getId()));
        dto.setName(sponsorship.getName());
        dto.setBrand(sponsorship.getBrand());
        dto.setContent(sponsorship.getContent());
        dto.setMoney(sponsorship.getMoney());
        dto.setTimeFrom(sponsorship.getTimeFrom());
        dto.setTimeTo(sponsorship.getTimeTo());
        dto.setStatus(sponsorship.getStatus().name());
        dto.setCreatedByUserName(
                sponsorship.getCreatedBy() != null ? sponsorship.getCreatedBy().getUsername() : null);
        dto.setLastModifiedByUserName(
                sponsorship.getLastModifiedBy() != null
                        ? sponsorship.getLastModifiedBy().getUsername()
                        : null);
        dto.setCreatedAt(sponsorship.getCreatedDate());
        dto.setUpdatedAt(sponsorship.getLastModifiedDate());

        if (sponsorship.getSponsorshipHackathons() != null) {
            dto.setSponsorshipHackathons(sponsorship.getSponsorshipHackathons().stream()
                    .map(SponsorshipHackathonMapperManual::toDto)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    public static Sponsorship toEntity(SponsorshipDTO dto) {
        if (dto == null) return null;

        Sponsorship sponsorship = new Sponsorship();
        if (dto.getId() != null) {
            sponsorship.setId(Long.parseLong(dto.getId()));
        }
        sponsorship.setName(dto.getName());
        sponsorship.setBrand(dto.getBrand());
        sponsorship.setContent(dto.getContent());
        sponsorship.setMoney(dto.getMoney());
        sponsorship.setTimeFrom(dto.getTimeFrom());
        sponsorship.setTimeTo(dto.getTimeTo());
        sponsorship.setStatus(SponsorshipStatus.valueOf(dto.getStatus()));

        return sponsorship;
    }
}
