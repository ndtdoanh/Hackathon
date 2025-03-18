package com.hacof.hackathon.service.impl;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.entity.EventRegistration;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.EventRegistrationMapper;
import com.hacof.hackathon.repository.EventRegistrationRepository;
import com.hacof.hackathon.service.EventRegistrationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventRegistrationServiceImpl implements EventRegistrationService {
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRegistrationMapper eventRegistrationMapper;

    @Override
    public EventRegistrationDTO registerForEvent(Long eventId, Long userId) {
        EventRegistration registration = new EventRegistration();
        // Set event and user
        registration = eventRegistrationRepository.save(registration);
        return eventRegistrationMapper.toDTO(registration);
    }

    @Override
    public EventRegistrationDTO approveRegistration(Long registrationId, Long adminId) {
        EventRegistration registration = eventRegistrationRepository
                .findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
        registration.setStatus("APPROVED");
        registration = eventRegistrationRepository.save(registration);
        return eventRegistrationMapper.toDTO(registration);
    }

    @Override
    public EventRegistrationDTO rejectRegistration(Long registrationId, Long adminId) {
        EventRegistration registration = eventRegistrationRepository
                .findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
        registration.setStatus("REJECTED");
        registration = eventRegistrationRepository.save(registration);
        return eventRegistrationMapper.toDTO(registration);
    }
}
