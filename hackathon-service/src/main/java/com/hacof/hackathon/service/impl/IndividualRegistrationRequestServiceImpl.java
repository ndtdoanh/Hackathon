package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.exception.InvalidInputException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.IndividualRegistrationRequestMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.IndividualRegistrationRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IndividualRegistrationRequestServiceImpl implements IndividualRegistrationRequestService {
    IndividualRegistrationRequestRepository requestRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    IndividualRegistrationRequestMapper requestMapper;

    //        @Override
    //        public IndividualRegistrationRequestDTO createIndividualRegistrationRequest(Long userId) {
    //    //        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not
    // found"));
    //    //        Hackathon hackathon = hackathonRepository.findById(user.getHackathon().getId())
    //    //                .orElseThrow(() -> new RuntimeException("Hackathon not found"));
    //    //
    //    //        IndividualRegistrationRequest request = IndividualRegistrationRequest.builder()
    //    //                .hackathon(hackathon)
    //    //                .status(IndividualRegistrationRequestStatus.PENDING)
    //    //                .createdBy(user)
    //    //                .build();
    //    //        requestRepository.save(request);
    //    //        return requestMapper.toDto(request);
    //            return null;
    //        }
    //
    //        @Override
    //        public IndividualRegistrationRequestDTO update(
    //                Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
    //            log.info("Updating individual registration request with id: {}", id);
    //
    //            IndividualRegistrationRequest request = requestRepository
    //                    .findById(id)
    //                    .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not
    // found"));
    //
    //            Hackathon hackathon = hackathonRepository
    //                    .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
    //                    .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    //
    //            User reviewedBy = userRepository
    //                    .findById(Long.parseLong(individualRegistrationRequestDTO.getReviewedById()))
    //                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    //
    //            requestMapper.updateEntityFromDto(individualRegistrationRequestDTO, request);
    //            request.setHackathon(hackathon);
    //            request.setReviewedBy(reviewedBy);
    //
    //            request = requestRepository.save(request);
    //            return requestMapper.toDto(request);
    //        }
    //
    //        @Override
    //        public void delete(Long id) {
    //            log.info("Deleting individual registration request with id: {}", id);
    //            if (!requestRepository.existsById(id)) {
    //                throw new ResourceNotFoundException("Individual registration request not found");
    //            }
    //            requestRepository.deleteById(id);
    //        }
    //
    //        @Override
    //        public List<IndividualRegistrationRequestDTO> getAll() {
    //            log.info("Fetching all individual registration requests");
    //            if (requestRepository.findAll().isEmpty()) {
    //                throw new ResourceNotFoundException("No individual registration requests found");
    //            }
    //            return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
    //        }
    //
    //        @Override
    //        public IndividualRegistrationRequestDTO getById(Long id) {
    //            log.info("Fetching individual registration request with id: {}", id);
    //            IndividualRegistrationRequest request = requestRepository
    //                    .findById(id)
    //                    .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not
    // found"));
    //            return requestMapper.toDto(request);
    //        }
    //
    //        @Override
    //        public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsername(String createdByUsername) {
    //            List<IndividualRegistrationRequest> requests =
    //     requestRepository.findAllByCreatedByUsername(createdByUsername);
    //            return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    //        }
    //
    //        @Override
    //        public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(
    //                String createdByUsername, String hackathonId) {
    //            List<IndividualRegistrationRequest> requests =
    // requestRepository.findAllByCreatedByUsernameAndHackathonId(
    //                    createdByUsername, Long.parseLong(hackathonId));
    //            return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    //        }
    @Override
    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {

        IndividualRegistrationRequest request = requestMapper.toEntity(individualRegistrationRequestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public IndividualRegistrationRequestDTO update(
            Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        IndividualRegistrationRequest request = requestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));
        requestMapper.updateEntityFromDto(individualRegistrationRequestDTO, request);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public void delete(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Individual registration request not found");
        }
        requestRepository.deleteById(id);
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAll() {
        return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public IndividualRegistrationRequestDTO getById(Long id) {
        IndividualRegistrationRequest request = requestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));
        return requestMapper.toDto(request);
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsername(String createdByUsername) {
        if(createdByUsername == null || createdByUsername.isEmpty()) {
            throw new InvalidInputException("Created by username cannot be null or empty");
        }
        if(requestRepository.findAllByCreatedByUsername(createdByUsername).isEmpty()) {
            throw new ResourceNotFoundException("No individual registration requests found for the given username");
        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByCreatedByUsername(createdByUsername);
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(String createdByUsername, String hackathonId) {
        if(createdByUsername == null || createdByUsername.isEmpty()) {
            throw new InvalidInputException("Created by username cannot be null or empty");
        }
        if(requestRepository.findAllByCreatedByUsernameAndHackathonId(createdByUsername, Long.parseLong(hackathonId)).isEmpty()) {
            throw new ResourceNotFoundException("No individual registration requests found for the given username and hackathon ID");
        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByCreatedByUsernameAndHackathonId(createdByUsername, Long.parseLong(hackathonId));
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonId(String hackathonId) {
        if(hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        if(requestRepository.findAllByHackathonId(Long.parseLong(hackathonId)).isEmpty()) {
            throw new ResourceNotFoundException("No individual registration requests found for the given hackathon ID");
        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByHackathonId(Long.parseLong(hackathonId));
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusApproved(String hackathonId) {
        if(hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        if(requestRepository.findAllByHackathonIdAndStatus(Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.APPROVED).isEmpty()) {
            throw new ResourceNotFoundException("No individual registration requests found for the given hackathon ID and status");
        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByHackathonIdAndStatus(Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.APPROVED);
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }
}
