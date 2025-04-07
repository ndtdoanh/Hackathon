package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.entity.EventRegistration;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.EventRegistrationMapper;
import com.hacof.hackathon.repository.EventRegistrationRepository;
import com.hacof.hackathon.service.EventRegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EventRegistrationServiceImpl implements EventRegistrationService {
    EventRegistrationRepository eventRegistrationRepository;
    EventRegistrationMapper eventRegistrationMapper;

    @Override
    public EventRegistrationDTO create(EventRegistrationDTO eventRegistrationDTO) {
        log.info("Creating new event registration");
        EventRegistration eventRegistration = eventRegistrationMapper.toEntity(eventRegistrationDTO);
        eventRegistration = eventRegistrationRepository.save(eventRegistration);
        return eventRegistrationMapper.toDto(eventRegistration);
    }

    @Override
    public EventRegistrationDTO update(Long id, EventRegistrationDTO eventRegistrationDTO) {
        log.info("Updating event registration with id: {}", id);
        EventRegistration eventRegistration = eventRegistrationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event registration not found"));
        eventRegistrationMapper.updateEntityFromDto(eventRegistrationDTO, eventRegistration);

        eventRegistration = eventRegistrationRepository.save(eventRegistration);
        return eventRegistrationMapper.toDto(eventRegistration);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting event registration with id: {}", id);
        if (!eventRegistrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event registration not found");
        }
        eventRegistrationRepository.deleteById(id);
    }

    @Override
    public List<EventRegistrationDTO> getAll() {
        log.info("Fetching all event registrations");
        return eventRegistrationRepository.findAll().stream()
                .map(eventRegistrationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRegistrationDTO getById(Long id) {
        log.info("Fetching event registration with id: {}", id);
        EventRegistration eventRegistration = eventRegistrationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event registration not found"));
        return eventRegistrationMapper.toDto(eventRegistration);
    }
}
