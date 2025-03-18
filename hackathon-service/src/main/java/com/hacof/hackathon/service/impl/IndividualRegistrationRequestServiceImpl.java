package com.hacof.hackathon.service.impl;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.IndividualRegistrationRequestMapper;
import com.hacof.hackathon.repository.IndividualRegistrationRequestRepository;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndividualRegistrationRequestServiceImpl implements IndividualRegistrationRequestService {
    private final IndividualRegistrationRequestRepository requestRepository;
    private final IndividualRegistrationRequestMapper requestMapper;

    @Override
    public IndividualRegistrationRequestDTO approveRequest(Long requestId, Long adminId) {
        IndividualRegistrationRequest request = requestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setStatus(Status.APPROVED);
        request = requestRepository.save(request);
        return requestMapper.toDTO(request);
    }

    @Override
    public IndividualRegistrationRequestDTO rejectRequest(Long requestId, Long adminId) {
        IndividualRegistrationRequest request = requestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setStatus(Status.REJECTED);
        request = requestRepository.save(request);
        return requestMapper.toDTO(request);
    }
}
