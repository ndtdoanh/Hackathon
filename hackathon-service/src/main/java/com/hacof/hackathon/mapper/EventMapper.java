package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.entity.Event;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    @Mapping(target = "hackathonId", source = "hackathon.id")
    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "organizerName", source = "organizer.username")
    @Mapping(target = "hackathonName", source = "hackathon.name")
    EventDTO toDTO(Event event);

    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    Event toEntity(EventDTO eventDTO);

    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    void updateEntityFromDTO(EventDTO eventDTO, @MappingTarget Event event);
}
