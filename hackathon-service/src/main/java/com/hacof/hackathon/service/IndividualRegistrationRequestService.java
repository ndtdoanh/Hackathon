package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;

public interface IndividualRegistrationRequestService {
    IndividualRegistrationRequestDTO approveRequest(Long requestId, Long adminId);

    IndividualRegistrationRequestDTO rejectRequest(Long requestId, Long adminId);
}
