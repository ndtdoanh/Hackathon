package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.EventRegistrationDTO;

public interface EventRegistrationService {
    EventRegistrationDTO create(EventRegistrationDTO eventRegistrationDTO);

    EventRegistrationDTO update(Long id, EventRegistrationDTO eventRegistrationDTO);

    void delete(Long id);

    List<EventRegistrationDTO> getAll();

    EventRegistrationDTO getById(Long id);
}
