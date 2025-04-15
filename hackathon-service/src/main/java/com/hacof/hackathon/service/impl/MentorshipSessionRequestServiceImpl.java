package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.entity.MentorshipSessionRequest;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.MentorshipSessionRequestMapperManual;
import com.hacof.hackathon.repository.MentorTeamRepository;
import com.hacof.hackathon.repository.MentorshipSessionRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipSessionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class MentorshipSessionRequestServiceImpl implements MentorshipSessionRequestService {
    MentorshipSessionRequestRepository mentorshipSessionRequestRepository;
    MentorTeamRepository mentorTeamRepository;
    UserRepository userRepository;

    @Override
    public MentorshipSessionRequestDTO create(MentorshipSessionRequestDTO mentorshipSessionRequestDTO) {
        validateForeignKeys(mentorshipSessionRequestDTO);
        // Convert DTO to Entity (manual mapper handles setting mentorTeam & evaluatedBy by ID)
        MentorshipSessionRequest mentorshipSessionRequest =
                MentorshipSessionRequestMapperManual.toEntity(mentorshipSessionRequestDTO);

        mentorshipSessionRequest = mentorshipSessionRequestRepository.save(mentorshipSessionRequest);
        return MentorshipSessionRequestMapperManual.toDto(mentorshipSessionRequest);
    }

    @Override
    public MentorshipSessionRequestDTO update(String id, MentorshipSessionRequestDTO mentorshipSessionRequestDTO) {
        validateForeignKeys(mentorshipSessionRequestDTO);

        MentorshipSessionRequest existingRequest = getMentorshipSessionRequest(id);

        MentorshipSessionRequestMapperManual.updateEntityFromDto(mentorshipSessionRequestDTO, existingRequest);

        if (mentorshipSessionRequestDTO.getMentorTeamId() != null) {
            MentorTeam mentorTeam = mentorTeamRepository
                    .findById(Long.parseLong(mentorshipSessionRequestDTO.getMentorTeamId()))
                    .orElseThrow(() -> new ResourceNotFoundException("MentorTeam not found"));
            existingRequest.setMentorTeam(mentorTeam);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        existingRequest.setLastModifiedBy(currentUser);
        existingRequest.setLastModifiedDate(LocalDateTime.now());

        MentorshipSessionRequest updated = mentorshipSessionRequestRepository.save(existingRequest);
        return MentorshipSessionRequestMapperManual.toDto(updated);
    }

    @Override
    public MentorshipSessionRequestDTO approveOrReject(
            String id, MentorshipSessionRequestDTO mentorshipSessionRequestDTO) {
        MentorshipSessionRequest existingRequest = getMentorshipSessionRequest(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        existingRequest.setEvaluatedBy(currentUser);

        MentorshipSessionRequestMapperManual.updateEntityFromDto(mentorshipSessionRequestDTO, existingRequest);

        MentorshipSessionRequest updated = mentorshipSessionRequestRepository.save(existingRequest);
        return MentorshipSessionRequestMapperManual.toDto(updated);
    }

    @Override
    public void delete(String id) {
        if (!mentorshipSessionRequestRepository.existsById(Long.parseLong(id))) {
            throw new ResourceNotFoundException("MentorshipSessionRequest not found");
        }
        mentorshipSessionRequestRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<MentorshipSessionRequestDTO> getAll() {
        //        if (mentorshipSessionRequestRepository.findAll().isEmpty()) {
        //            throw new ResourceNotFoundException("No MentorshipSessionRequest found");
        //        }
        return mentorshipSessionRequestRepository.findAll().stream()
                .map(MentorshipSessionRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MentorshipSessionRequestDTO getById(String id) {
        //        if (!mentorshipSessionRequestRepository.existsById(Long.parseLong(id))) {
        //            throw new ResourceNotFoundException("MentorshipSessionRequest not found");
        //        }
        return MentorshipSessionRequestMapperManual.toDto(getMentorshipSessionRequest(id));
    }

    @Override
    public List<MentorshipSessionRequestDTO> getAllByMentorTeamId(String mentorTeamId) {
        //        if (mentorshipSessionRequestRepository
        //                .findAllByMentorTeamId(Long.parseLong(mentorTeamId))
        //                .isEmpty()) {
        //            throw new ResourceNotFoundException("No MentorshipSessionRequest found");
        //        }
        return mentorshipSessionRequestRepository.findAllByMentorTeamId(Long.parseLong(mentorTeamId)).stream()
                .map(MentorshipSessionRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    private void validateForeignKeys(MentorshipSessionRequestDTO mentorshipSessionRequestDTO) {
        if (!mentorTeamRepository.existsById(Long.parseLong(mentorshipSessionRequestDTO.getMentorTeamId()))) {
            throw new ResourceNotFoundException("MentorTeam not found");
        }
        if (mentorshipSessionRequestDTO.getEvaluatedById() != null
                && !userRepository.existsById(Long.parseLong(mentorshipSessionRequestDTO.getEvaluatedById()))) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    private MentorshipSessionRequest getMentorshipSessionRequest(String id) {
        return mentorshipSessionRequestRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("MentorshipSessionRequest not found"));
    }
}
