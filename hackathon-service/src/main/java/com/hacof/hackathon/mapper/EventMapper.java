package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "hackathonId", source = "hackathon.id")
    @Mapping(target = "organizerId", source = "organizer.id")
    EventDTO toDto(Event event);

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "organizer.id", source = "organizerId")
    Event toEntity(EventDTO eventDTO);

    void updateEntityFromDto(EventDTO dto, @MappingTarget Event entity);
}
