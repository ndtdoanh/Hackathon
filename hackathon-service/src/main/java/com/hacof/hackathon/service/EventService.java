package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.entity.Event;

public interface EventService {
    EventDTO createEvent(EventDTO eventDTO);

    EventDTO updateEvent(Long id, EventDTO eventDTO);

    void deleteEvent(Long id);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(Long id);

    List<EventDTO> getEventsByHackathonId(Long hackathonId);

    List<EventDTO> getEventsByOrganizerId(Long organizerId);

    List<EventDTO> searchEvents(Specification<Event> spec);
}
