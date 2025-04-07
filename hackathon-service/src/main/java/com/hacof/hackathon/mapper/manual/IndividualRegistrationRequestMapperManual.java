package com.hacof.hackathon.mapper.manual;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;

public class IndividualRegistrationRequestMapperManual {

    public static IndividualRegistrationRequest toEntity(
            IndividualRegistrationRequestDTO dto, Hackathon hackathon, User reviewedBy) {
        if (dto == null) {
            return null;
        }

        return IndividualRegistrationRequest.builder()
                .hackathon(hackathon)
                .status(IndividualRegistrationRequestStatus.valueOf(dto.getStatus()))
                .reviewedBy(reviewedBy)
                .build();
    }

    public static IndividualRegistrationRequestDTO toDto(IndividualRegistrationRequest entity) {
        if (entity == null) {
            return null;
        }

        IndividualRegistrationRequestDTO dto = new IndividualRegistrationRequestDTO();
        dto.setId(String.valueOf(entity.getId()));
        dto.setHackathonId(String.valueOf(entity.getHackathon().getId()));
        dto.setStatus(entity.getStatus().name());
        dto.setReviewedBy(UserMapperManual.toDto(entity.getReviewedBy()));
        //        dto.setCreatedByUserName(entity.getCreatedBy());
        //        dto.setCreatedAt(entity.getCreatedAt());
        //        dto.setLastModifiedByUserName(entity.getLastModifiedByUserName());
        //        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}
