package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;

@Mapper(componentModel = "spring")
public interface IndividualRegistrationRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(individualRegistrationRequest.getId()))")
    @Mapping(
            target = "hackathonId",
            expression = "java(String.valueOf(individualRegistrationRequest.getHackathon().getId()))")
    @Mapping(
            target = "reviewedById",
            expression = "java(String.valueOf(individualRegistrationRequest.getReviewedBy().getId()))")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(individualRegistrationRequest.getCreatedBy() != null ? individualRegistrationRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(individualRegistrationRequest.getLastModifiedBy() != null ? individualRegistrationRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    IndividualRegistrationRequestDTO toDto(IndividualRegistrationRequest individualRegistrationRequest);

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "reviewedBy.id", source = "reviewedById")
    IndividualRegistrationRequest toEntity(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    void updateEntityFromDto(IndividualRegistrationRequestDTO dto, @MappingTarget IndividualRegistrationRequest entity);
}
