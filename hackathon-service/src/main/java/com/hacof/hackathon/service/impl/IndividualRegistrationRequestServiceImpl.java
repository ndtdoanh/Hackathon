package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.IndividualRegistrationRequestMapper;
import com.hacof.hackathon.repository.IndividualRegistrationRequestRepository;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IndividualRegistrationRequestServiceImpl implements IndividualRegistrationRequestService {
    private final IndividualRegistrationRequestRepository requestRepository;
    private final IndividualRegistrationRequestMapper requestMapper;

    @Override
    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        log.info("Creating new individual registration request");
        IndividualRegistrationRequest request = requestMapper.toEntity(individualRegistrationRequestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public IndividualRegistrationRequestDTO update(
            Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        log.info("Updating individual registration request with id: {}", id);
        IndividualRegistrationRequest request = requestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));
        requestMapper.updateEntityFromDto(individualRegistrationRequestDTO, request);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting individual registration request with id: {}", id);
        if (!requestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Individual registration request not found");
        }
        requestRepository.deleteById(id);
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAll() {
        log.info("Fetching all individual registration requests");
        return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public IndividualRegistrationRequestDTO getById(Long id) {
        log.info("Fetching individual registration request with id: {}", id);
        IndividualRegistrationRequest request = requestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));
        return requestMapper.toDto(request);
    }
}
