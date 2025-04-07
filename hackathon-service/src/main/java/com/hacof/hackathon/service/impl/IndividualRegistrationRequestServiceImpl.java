package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

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
    IndividualRegistrationRequestMapper requestMapper;
    HackathonMapper hackathonMapper;

    @Override
    public IndividualRegistrationRequestDTO create(IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        if (individualRegistrationRequestDTO.getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User reviewedBy = userRepository
                .findById(Long.parseLong(
                        individualRegistrationRequestDTO.getReviewedBy().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        IndividualRegistrationRequest request = IndividualRegistrationRequest.builder()
                .hackathon(hackathon)
                .status(IndividualRegistrationRequestStatus.valueOf(individualRegistrationRequestDTO.getStatus()))
                .reviewedBy(reviewedBy)
                .build();

        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public IndividualRegistrationRequestDTO update(Long id, IndividualRegistrationRequestDTO individualRegistrationRequestDTO) {
        // 1. Kiểm tra tồn tại của request cần cập nhật
        IndividualRegistrationRequest existingRequest = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Individual registration request not found"));

        // 2. Kiểm tra hackathonId có null không
        if (individualRegistrationRequestDTO.getHackathonId() == null) {
            throw new InvalidInputException("Hackathon ID cannot be null");
        }

        // 3. Tìm Hackathon entity từ hackathonId
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        // 4. Tìm reviewedBy user
        User reviewedBy = userRepository
                .findById(Long.parseLong(individualRegistrationRequestDTO.getReviewedBy().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 5. Cập nhật dữ liệu
        existingRequest.setHackathon(hackathon);
        existingRequest.setReviewedBy(reviewedBy);
        existingRequest.setStatus(IndividualRegistrationRequestStatus.valueOf(individualRegistrationRequestDTO.getStatus()));

//        existingRequest.setL(currentUsername);
//        existingRequest.setUpdatedAt(LocalDateTime.now());
        // 6. Lưu và trả về DTO
        IndividualRegistrationRequest updatedRequest = requestRepository.save(existingRequest);
        return requestMapper.toDto(updatedRequest);
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
        return requestRepository.findById(id)
                .map(requestMapper::toDto)
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
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
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
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
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
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
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
        return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }
}
