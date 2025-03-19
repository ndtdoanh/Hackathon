package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.entity.Event;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.EventMapper;
import com.hacof.hackathon.repository.EventRepository;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final HackathonRepository hackathonRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        log.info("Creating new event with name: {}", eventDTO.getName());
        Event event = eventMapper.toEntity(eventDTO);

        // Set hackathon if provided
        if (eventDTO.getHackathonId() != null) {
            Hackathon hackathon = hackathonRepository
                    .findById(eventDTO.getHackathonId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Hackathon not found with id: " + eventDTO.getHackathonId()));
            event.setHackathon(hackathon);
        }

        // Set organizer (required)
        if (eventDTO.getOrganizerId() == null) {
            throw new ResourceNotFoundException("OrganizerId is required");
        }
        User organizer = userRepository
                .findById(eventDTO.getOrganizerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with id: " + eventDTO.getOrganizerId()));
        event.setOrganizer(organizer);

        event = eventRepository.save(event);
        log.info("Created new event with id: {}", event.getId());
        return eventMapper.toDTO(event);
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        log.info("Updating event with id: {}", id);
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        // Update basic fields
        eventMapper.updateEntityFromDTO(eventDTO, event);

        // Update hackathon if provided
        if (eventDTO.getHackathonId() != null) {
            Hackathon hackathon = hackathonRepository
                    .findById(eventDTO.getHackathonId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Hackathon not found with id: " + eventDTO.getHackathonId()));
            event.setHackathon(hackathon);
        }

        // Update organizer if provided
        if (eventDTO.getOrganizerId() != null) {
            User organizer = userRepository
                    .findById(eventDTO.getOrganizerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User not found with id: " + eventDTO.getOrganizerId()));
            event.setOrganizer(organizer);
        }

        event = eventRepository.save(event);
        log.info("Updated event with id: {}", event.getId());
        return eventMapper.toDTO(event);
    }

    @Override
    public void deleteEvent(Long id) {
        log.info("Deleting event with id: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
        log.info("Deleted event with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvents() {
        log.info("Fetching all events");
        return eventRepository.findAll().stream().map(eventMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO getEventById(Long id) {
        log.info("Fetching event with id: {}", id);
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return eventMapper.toDTO(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getEventsByHackathonId(Long hackathonId) {
        log.info("Fetching events for hackathon id: {}", hackathonId);
        return eventRepository.findByHackathonId(hackathonId).stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getEventsByOrganizerId(Long organizerId) {
        log.info("Fetching events for organizer id: {}", organizerId);
        return eventRepository.findByOrganizerId(organizerId).stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> searchEvents(Specification<Event> spec) {
        log.info("Searching events with specification");
        return eventRepository.findAll(spec).stream().map(eventMapper::toDTO).collect(Collectors.toList());
    }
}
