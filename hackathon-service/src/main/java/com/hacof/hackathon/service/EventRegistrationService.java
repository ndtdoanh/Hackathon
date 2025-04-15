package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.EventRegistrationDTO;

import java.util.List;

public interface EventRegistrationService {
    EventRegistrationDTO create(EventRegistrationDTO eventRegistrationDTO);

    EventRegistrationDTO update(Long id, EventRegistrationDTO eventRegistrationDTO);

    void delete(Long id);

    List<EventRegistrationDTO> getAll();

    EventRegistrationDTO getById(Long id);
}
