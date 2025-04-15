package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.entity.Event;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.EventMapper;
import com.hacof.hackathon.repository.EventRepository;
import com.hacof.hackathon.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventDTO create(EventDTO eventDTO) {
        log.info("Creating new event with name: {}", eventDTO.getName());
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public EventDTO update(Long id, EventDTO eventDTO) {
        log.info("Updating event with id: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        eventMapper.updateEntityFromDto(eventDTO, event);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting event with id: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDTO> getAll() {
        log.info("Fetching all events");
        if (eventRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No events found");
        }
        return eventRepository.findAll().stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDTO getById(Long id) {
        log.info("Fetching event with id: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found");
        }
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return eventMapper.toDto(event);
    }
}
