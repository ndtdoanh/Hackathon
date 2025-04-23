package com.hacof.hackathon.mapper.manual;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;

public class IndividualRegistrationRequestMapperManual {

    public static IndividualRegistrationRequest toEntity(
            IndividualRegistrationRequestDTO dto, Hackathon hackathon, User reviewedBy) {
        if (dto == null) return null;

        return IndividualRegistrationRequest.builder()
                .id(dto.getId() != null ? Long.parseLong(dto.getId()) : null)
                .hackathon(hackathon)
                .status(IndividualRegistrationRequestStatus.valueOf(dto.getStatus()))
                .reviewedBy(reviewedBy)
                .build();
    }

    public static IndividualRegistrationRequestDTO toDto(IndividualRegistrationRequest entity) {
        if (entity == null) return null;

        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId(entity.getId() != null && entity.getId() != 0 ? String.valueOf(entity.getId()) : null);
        dto.setHackathonId(
                entity.getHackathon() != null
                        ? String.valueOf(entity.getHackathon().getId())
                        : null);
        dto.setHackathon(entity.getHackathon() != null ? HackathonMapperManual.toDto(entity.getHackathon()) : null);
        dto.setStatus(entity.getStatus().name());
        dto.setReviewedById(
                entity.getReviewedBy() != null
                        ? String.valueOf(entity.getReviewedBy().getId())
                        : null);
        dto.setReviewedBy(entity.getReviewedBy() != null ? UserMapperManual.toDto(entity.getReviewedBy()) : null);
        dto.setCreatedByUserName(
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setLastModifiedByUserName(
                entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setUpdatedAt(entity.getLastModifiedDate());

        return dto;
    }

    public static void updateEntityFromDto(
            IndividualRegistrationRequestDTO dto,
            IndividualRegistrationRequest entity,
            Hackathon hackathon,
            User reviewedBy,
            User currentUser) {

        if (dto == null || entity == null) return;

        entity.setHackathon(hackathon);
        entity.setReviewedBy(reviewedBy);
        entity.setStatus(IndividualRegistrationRequestStatus.valueOf(dto.getStatus()));
        entity.setLastModifiedBy(currentUser);
        entity.setLastModifiedDate(LocalDateTime.now());
    }
}
