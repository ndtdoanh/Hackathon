package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.dto.IndividualRegistrationBulkRequestDTO;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.IndividualRegistrationRequestMapperManual;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.IndividualRegistrationRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.IndividualRegistrationRequestService;
import com.hacof.hackathon.util.SecurityUtil;

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
    SecurityUtil securityUtil;

    @Override
    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        if (individualRegistrationRequestDTO.getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        // validate enrollment period
        validateEnrollPeriod(hackathon);

        User reviewedBy = null;
        if (individualRegistrationRequestDTO.getReviewedById() != null
                && !individualRegistrationRequestDTO.getReviewedById().isEmpty()) {
            reviewedBy = userRepository
                    .findById(Long.parseLong(individualRegistrationRequestDTO.getReviewedById()))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        IndividualRegistrationRequest request = IndividualRegistrationRequestMapperManual.toEntity(
                individualRegistrationRequestDTO, hackathon, reviewedBy);

        request = requestRepository.save(request);
        IndividualRegistrationRequestDTO responseDTO = IndividualRegistrationRequestMapperManual.toDto(request);
        responseDTO.setReviewedById(individualRegistrationRequestDTO.getReviewedById()); // Use setter instead
        return responseDTO;
    }

    @Override
    public List<IndividualRegistrationRequestDTO> createBulk(
            List<IndividualRegistrationBulkRequestDTO> bulkRequestDTOList) {
        if (bulkRequestDTOList == null || bulkRequestDTOList.isEmpty()) {
            throw new InvalidInputException("Bulk request cannot be empty");
        }

        List<IndividualRegistrationRequestDTO> resultList = new ArrayList<>();

        for (IndividualRegistrationBulkRequestDTO bulkRequestDTO : bulkRequestDTOList) {
            // log.debug("Processing bulk request: {}", bulkRequestDTO.toString());
            Hackathon hackathon = hackathonRepository
                    .findById(Long.parseLong(bulkRequestDTO.getHackathonId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
            // log.debug("Fetched hackathon: {}", hackathon.toString());

            User createdByUser = userRepository
                    .findById(Long.parseLong(bulkRequestDTO.getCreatedByUserId()))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            log.debug("Fetched createdBy user: {}", createdByUser.toString());

            IndividualRegistrationRequest request = new IndividualRegistrationRequest();
            request.setHackathon(hackathon);
            request.setStatus(IndividualRegistrationRequestStatus.valueOf(bulkRequestDTO.getStatus()));
            request.setCreatedBy(createdByUser);
            log.debug("Created IndividualRegistrationRequest: {}", request.getCreatedBy());

            request = requestRepository.save(request);
            // log.debug("Saved IndividualRegistrationRequest: {}", request.toString());

            IndividualRegistrationRequestDTO responseDTO = IndividualRegistrationRequestMapperManual.toDto(request);
            responseDTO.setCreatedByUserName(createdByUser.getUsername());
            // log.debug("Mapped response DTO: {}", responseDTO.toString());
            resultList.add(responseDTO);
        }

        return resultList;
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
        String reviewById = individualRegistrationRequestDTO.getReviewedById();
        if (reviewById != null && !reviewById.trim().isEmpty()) {
            reviewedBy = userRepository
                    .findById(Long.parseLong(reviewById))
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + reviewById));
        }

        // Get the current authenticated user
        User currentUser = securityUtil.getAuthenticatedUser();

        IndividualRegistrationRequestMapperManual.updateEntityFromDto(
                individualRegistrationRequestDTO, existingRequest, hackathon, reviewedBy, currentUser);

        IndividualRegistrationRequest updatedRequest = requestRepository.save(existingRequest);
        IndividualRegistrationRequestDTO responseDTO = IndividualRegistrationRequestMapperManual.toDto(updatedRequest);
        responseDTO.setReviewedById(reviewById);
        return responseDTO;
    }

    @Override
    public List<IndividualRegistrationRequestDTO> bulkUpdate(List<IndividualRegistrationRequestDTO> requestDTOs) {
        return requestDTOs.stream()
                .map(dto -> update(Long.parseLong(dto.getId()), dto)) // Reuse the existing update method
                .collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusApproved(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }
        List<IndividualRegistrationRequest> requests = requestRepository.findAllByHackathonIdAndStatus(
                Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.APPROVED);
        return requests.stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
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
        return requestRepository.findAll().stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IndividualRegistrationRequestDTO getById(Long id) {
        return requestRepository
                .findById(id)
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .orElse(null);
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
        return requests.stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, String hackathonId) {
        if (createdByUsername == null || createdByUsername.isEmpty()) {
            throw new InvalidInputException("Created by username cannot be null or empty");
        }

        List<IndividualRegistrationRequest> requests = requestRepository.findAllByCreatedByUsernameAndHackathonId(
                createdByUsername, Long.parseLong(hackathonId));
        return requests.stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
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
        return requests.stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IndividualRegistrationRequestDTO> getAllByHackathonIdAndStatusCompleted(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID cannot be null or empty");
        }

        List<IndividualRegistrationRequest> requests = requestRepository.findAllByHackathonIdAndStatus(
                Long.parseLong(hackathonId), IndividualRegistrationRequestStatus.COMPLETED);
        return requests.stream()
                .map(IndividualRegistrationRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    private void validateEnrollPeriod(Hackathon hackathon) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(hackathon.getEnrollStartDate()) || now.isAfter(hackathon.getEnrollEndDate())) {
            throw new InvalidInputException("Cannot individual create request outside of enrollment period");
        }
    }
}
