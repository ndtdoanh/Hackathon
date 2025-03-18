package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.EventRegistrationDTO;

public interface EventRegistrationService {
    EventRegistrationDTO registerForEvent(Long eventId, Long userId);

    EventRegistrationDTO approveRegistration(Long registrationId, Long adminId);

    EventRegistrationDTO rejectRegistration(Long registrationId, Long adminId);
}
