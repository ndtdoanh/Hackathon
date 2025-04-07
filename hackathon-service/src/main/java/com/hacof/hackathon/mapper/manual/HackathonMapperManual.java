package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.CategoryStatus;
import com.hacof.hackathon.constant.OrganizationStatus;
import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;

public class HackathonMapperManual {

    public static HackathonDTO toDto(Hackathon entity) {
        if (entity == null) {
            return null;
        }

        HackathonDTO dto = new HackathonDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setTitle(entity.getTitle());
        dto.setSubTitle(entity.getSubTitle());
        dto.setBannerImageUrl(entity.getBannerImageUrl());
        dto.setEnrollStartDate(entity.getEnrollStartDate());
        dto.setEnrollEndDate(entity.getEnrollEndDate());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setInformation(entity.getInformation());
        dto.setDescription(entity.getDescription());
        dto.setContact(entity.getContact());

        // Check if category is not null before calling name()
        if (entity.getCategory() != null) {
            dto.setCategory(entity.getCategory().name());
        }

        dto.setOrganization(entity.getOrganization().name());
        dto.setStatus(entity.getStatus().name());

        return dto;
    }

    public static Hackathon toEntity(HackathonDTO dto) {
        if (dto == null) {
            return null;
        }

        Hackathon hackathon = new Hackathon();
        hackathon.setId(Long.parseLong(dto.getId()));
        hackathon.setTitle(dto.getTitle());
        hackathon.setSubTitle(dto.getSubTitle());
        hackathon.setBannerImageUrl(dto.getBannerImageUrl());
        hackathon.setEnrollStartDate(dto.getEnrollStartDate());
        hackathon.setEnrollEndDate(dto.getEnrollEndDate());
        hackathon.setStartDate(dto.getStartDate());
        hackathon.setEndDate(dto.getEndDate());
        hackathon.setInformation(dto.getInformation());
        hackathon.setDescription(dto.getDescription());
        hackathon.setContact(dto.getContact());
        // Set category if not null
        if (dto.getCategory() != null) {
            hackathon.setCategory(CategoryStatus.valueOf(dto.getCategory()));
        }
        hackathon.setOrganization(OrganizationStatus.valueOf(dto.getOrganization()));
        hackathon.setStatus(Status.valueOf(dto.getStatus()));

        return hackathon;
    }
}
