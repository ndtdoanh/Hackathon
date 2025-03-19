package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.entity.EventRegistration;

@Mapper(componentModel = "spring")
public interface EventRegistrationMapper {
    EventRegistrationDTO toDTO(EventRegistration eventRegistration);

    EventRegistration toEntity(EventRegistrationDTO eventRegistrationDTO);
}
