package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;

@Mapper(componentModel = "spring")
public interface IndividualRegistrationRequestMapper {
    IndividualRegistrationRequestDTO toDto(IndividualRegistrationRequest individualRegistrationRequest);

    IndividualRegistrationRequest toEntity(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    void updateEntityFromDto(IndividualRegistrationRequestDTO dto, @MappingTarget IndividualRegistrationRequest entity);
}
