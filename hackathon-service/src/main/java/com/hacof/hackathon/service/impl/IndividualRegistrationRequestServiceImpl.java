package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hacof.hackathon.mapper.manual.IndividualRegistrationRequestMapperManual;
import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.HackathonMapper;
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
    //IndividualRegistrationRequestMapper requestMapper;
    HackathonMapper hackathonMapper;

//    @Override
//    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
//        if (individualRegistrationRequestDTO.getHackathonId() == null) {
//            throw new InvalidInputException("Hackathon ID cannot be null");
//        }
//        Hackathon hackathon = hackathonRepository
//                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
//                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
//
//        User reviewedBy = null;
//        if (individualRegistrationRequestDTO.getReviewById() != null
//                && !individualRegistrationRequestDTO.getReviewById().isEmpty()) {
//            reviewedBy = userRepository
//                    .findById(Long.parseLong(individualRegistrationRequestDTO.getReviewById()))
//                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        }
//
//        IndividualRegistrationRequest request = IndividualRegistrationRequest.builder()
//                .hackathon(hackathon)
//                .status(IndividualRegistrationRequestStatus.valueOf(individualRegistrationRequestDTO.getStatus()))
//                .reviewedBy(reviewedBy)
//                .build();
//
//        request = requestRepository.save(request);
//        return requestMapper.toDto(request);
//    }
//
//    @Override
//    public IndividualRegistrationRequestDTO update(
//            Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
//        IndividualRegistrationRequest existingRequest = requestRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));
//
//        if (individualRegistrationRequestDTO.getHackathonId() == null) {
//            throw new InvalidInputException("Hackathon ID cannot be null");
//        }
//
//        Hackathon hackathon = hackathonRepository
//                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
//                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
//
//        User reviewedBy = null;
//        String reviewById = individualRegistrationRequestDTO.getReviewById();
//        if (reviewById != null && !reviewById.trim().isEmpty()) {
//            reviewedBy = userRepository
//                    .findById(Long.parseLong(reviewById))
//                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + reviewById));
//        }
//
//        if (existingRequest.getStatus() == IndividualRegistrationRequestStatus.APPROVED
//                && !IndividualRegistrationRequestStatus.APPROVED
//                        .name()
//                        .equalsIgnoreCase(individualRegistrationRequestDTO.getStatus())) {
//            throw new InvalidInputException("Cannot update status after it's approved");
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new IllegalStateException("No authenticated user found");
//        }
//
//        String username = authentication.getName();
//        User currentUser = userRepository
//                .findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
//
//        existingRequest.setHackathon(hackathon);
//        existingRequest.setReviewedBy(reviewedBy);
//        existingRequest.setStatus(
//                IndividualRegistrationRequestStatus.valueOf(individualRegistrationRequestDTO.getStatus()));
//        existingRequest.setLastModifiedBy(currentUser);
//        existingRequest.setLastModifiedDate(LocalDateTime.now());
//
//        IndividualRegistrationRequest updatedRequest = requestRepository.save(existingRequest);
//
//        IndividualRegistrationRequestDTO responseDTO = requestMapper.toDto(updatedRequest);
//        responseDTO.setReviewById(reviewById);
//        return responseDTO;
//    }

    @Override
    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        if (individualRegistrationRequestDTO.getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User reviewedBy = null;
        if (individualRegistrationRequestDTO.getReviewById() != null
                && !individualRegistrationRequestDTO.getReviewById().isEmpty()) {
            reviewedBy = userRepository
                    .findById(Long.parseLong(individualRegistrationRequestDTO.getReviewById()))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        IndividualRegistrationRequest request = IndividualRegistrationRequestMapperManual
                .toEntity(individualRegistrationRequestDTO, hackathon, reviewedBy);

        request = requestRepository.save(request);
        IndividualRegistrationRequestDTO responseDTO = IndividualRegistrationRequestMapperManual.toDto(request);
        responseDTO.setReviewById(individualRegistrationRequestDTO.getReviewById());
        return responseDTO;
    }

    @Override
    public IndividualRegistrationRequestDTO update(
            Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {

        IndividualRegistrationRequest existingRequest = requestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));

        if (individualRegistrationRequestDTO.getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User reviewedBy = null;
        String reviewById = individualRegistrationRequestDTO.getReviewById();
        if (reviewById != null && !reviewById.trim().isEmpty()) {
            reviewedBy = userRepository
                    .findById(Long.parseLong(reviewById))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + reviewById));
        }

        if (existingRequest.getStatus() == IndividualRegistrationRequestStatus.APPROVED &&
                !IndividualRegistrationRequestStatus.APPROVED.name().equalsIgnoreCase(individualRegistrationRequestDTO.getStatus())) {
            throw new InvalidInputException("Cannot update status after it's approved");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        IndividualRegistrationRequestMapperManual
                .updateEntityFromDto(individualRegistrationRequestDTO, existingRequest, hackathon, reviewedBy, currentUser);

        IndividualRegistrationRequest updatedRequest = requestRepository.save(existingRequest);
        IndividualRegistrationRequestDTO responseDTO = IndividualRegistrationRequestMapperManual.toDto(updatedRequest);
        responseDTO.setReviewById(reviewById);
        return responseDTO;
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
        return requestRepository.findAll().stream().map(IndividualRegistrationRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public IndividualRegistrationRequestDTO getById(Long id) {
        return requestRepository.findById(id).map(IndividualRegistrationRequestMapperManual::toDto).orElse(null);
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsername(String createdByUsername) {
        if (createdByUsername == null || createdByUsername.isEmpty()) {
            throw new InvalidInputException("Created by username cannot be null or empty");
        }
        //        if (requestRepository.findAllByCreatedByUsername(createdByUsername).isEmpty()) {
        //            throw new ResourceNotFoundException("No individual registration requests found for the given
        // username");
        //        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByCreatedByUsername(createdByUsername);
        return requests.stream().map(IndividualRegistrationRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, String hackathonId) {
        if (createdByUsername == null || createdByUsername.isEmpty()) {
            throw new InvalidInputException("Created by username cannot be null or empty");
        }
        //        if (requestRepository
        //                .findAllByCreatedByUsernameAndHackathonId(createdByUsername, Long.parseLong(hackathonId))
        //                .isEmpty()) {
        //            throw new ResourceNotFoundException(
        //                    "No individual registration requests found for the given username and hackathon ID");
        //        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByCreatedByUsernameAndHackathonId(
                createdByUsername, Long.parseLong(hackathonId));
        return requests.stream().map(IndividualRegistrationRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonId(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        //        if (requestRepository.findAllByHackathonId(Long.parseLong(hackathonId)).isEmpty()) {
        //            throw new ResourceNotFoundException("No individual registration requests found for the given
        // hackathon ID");
        //        }
        List<IndividualRegistrationRequest> requests =
                requestRepository.findAllByHackathonId(Long.parseLong(hackathonId));
        return requests.stream().map(IndividualRegistrationRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusApproved(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        //        if (requestRepository
        //                .findAllByHackathonIdAndStatus(
        //                        Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.APPROVED)
        //                .isEmpty()) {
        //            throw new ResourceNotFoundException(
        //                    "No individual registration requests found for the given hackathon ID and status");
        //        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByHackathonIdAndStatus(
                Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.APPROVED);
        return requests.stream().map(IndividualRegistrationRequestMapperManual::toDto).collect(Collectors.toList());
    }
}
