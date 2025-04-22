package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.SponsorshipDetailStatus;
import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailRequestDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailResponseDTO;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SponsorshipHackathonDetailMapperManual {

    public static SponsorshipHackathonDetail toEntity(SponsorshipHackathonDetailRequestDTO dto) {
        if (dto == null) return null;

        SponsorshipHackathonDetail entity = new SponsorshipHackathonDetail();
        entity.setMoneySpent(dto.getMoneySpent());
        entity.setContent(dto.getContent());
        entity.setStatus(SponsorshipDetailStatus.valueOf(dto.getStatus()));
        entity.setTimeFrom(dto.getTimeFrom());
        entity.setTimeTo(dto.getTimeTo());
        return entity;
    }

    public static SponsorshipHackathonDetailResponseDTO toDto(SponsorshipHackathonDetail entity) {
        if (entity == null) return null;

        SponsorshipHackathonDetailResponseDTO dto = new SponsorshipHackathonDetailResponseDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setSponsorshipHackathonId(entity.getSponsorshipHackathon() != null
                ? String.valueOf(entity.getSponsorshipHackathon().getId())
                : null);
        dto.setMoneySpent(entity.getMoneySpent());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus().name());
        dto.setTimeFrom(entity.getTimeFrom());
        dto.setTimeTo(entity.getTimeTo());

        dto.setCreatedByUserName(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        dto.setCreatedAt(entity.getCreatedDate());
        dto.setLastModifiedByUserName(entity.getLastModifiedBy() != null ? entity.getLastModifiedBy().getUsername() : null);
        dto.setUpdatedAt(entity.getLastModifiedDate());

        List<FileUrlResponse> fileUrlResponses = entity.getFileUrls() != null
                ? entity.getFileUrls().stream()
                .map(f -> FileUrlResponse.builder()
                        .id(String.valueOf(f.getId()))
                        .fileUrl(f.getFileUrl())
                        .fileName(f.getFileName())
                        .fileType(f.getFileType())
                        .fileSize(f.getFileSize())
                        .createdAt(f.getCreatedDate())
                        .updatedAt(f.getLastModifiedDate())
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        dto.setFileUrls(fileUrlResponses);
        return dto;
    }

    public static void updateEntityFromDto(SponsorshipHackathonDetailRequestDTO dto, SponsorshipHackathonDetail entity) {
        if (dto == null || entity == null) return;

        entity.setMoneySpent(dto.getMoneySpent());
        entity.setContent(dto.getContent());
        entity.setStatus(SponsorshipDetailStatus.valueOf(dto.getStatus()));
        entity.setTimeFrom(dto.getTimeFrom());
        entity.setTimeTo(dto.getTimeTo());
        // File URLs are expected to be updated via separate `updateFiles(...)` endpoint, so can be skipped here
    }
}
