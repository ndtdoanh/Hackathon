package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;

@Mapper(componentModel = "spring")
public interface IndividualRegistrationRequestMapper {
    IndividualRegistrationRequestDTO toDTO(IndividualRegistrationRequest request);

    IndividualRegistrationRequest toEntity(IndividualRegistrationRequestDTO requestDTO);
}
