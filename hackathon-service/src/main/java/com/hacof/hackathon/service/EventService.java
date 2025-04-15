package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO create(EventDTO eventDTO);

    EventDTO update(Long id, EventDTO eventDTO);

    void delete(Long id);

    List<EventDTO> getAll();

    EventDTO getById(Long id);

    // List<EventDTO> getEventsByHackathonId(Long hackathonId);

    // List<EventDTO> getEventsByOrganizerId(Long organizerId);

    // List<EventDTO> searchEvents(Specification<Event> spec);
}
