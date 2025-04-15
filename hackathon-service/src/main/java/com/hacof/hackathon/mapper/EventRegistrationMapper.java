package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.entity.EventRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventRegistrationMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    EventRegistrationDTO toDto(EventRegistration eventRegistration);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "event.id", source = "eventId")
    EventRegistration toEntity(EventRegistrationDTO eventRegistrationDTO);

    void updateEntityFromDto(EventRegistrationDTO dto, @MappingTarget EventRegistration entity);
}
