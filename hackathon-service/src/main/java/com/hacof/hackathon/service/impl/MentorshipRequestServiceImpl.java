package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.constant.MentorshipStatus;
import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.MentorshipRequestMapper;
import com.hacof.hackathon.mapper.manual.MentorshipRequestMapperManual;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.MentorTeamRepository;
import com.hacof.hackathon.repository.MentorshipRequestRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MentorshipRequestServiceImpl implements MentorshipRequestService {
    MentorshipRequestRepository mentorshipRequestRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;
    MentorTeamRepository mentorTeamRepository;
    MentorshipRequestMapper mentorshipRequestMapper;

    @Override
    public MentorshipRequestDTO create(MentorshipRequestDTO mentorshipRequestDTO) {

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User evaluatedBy = null;
        if (mentorshipRequestDTO.getEvaluatedById() != null) {
            evaluatedBy = userRepository
                    .findById(parseLongSafely(mentorshipRequestDTO.getEvaluatedById()))
                    .orElseThrow(() -> new ResourceNotFoundException("EvaluatedBy User not found"));
        }

        MentorshipRequest mentorshipRequest = MentorshipRequestMapperManual.toEntity(mentorshipRequestDTO);
        mentorshipRequest.setHackathon(hackathon);
        mentorshipRequest.setMentor(mentor);
        mentorshipRequest.setTeam(team);
        mentorshipRequest.setEvaluatedBy(evaluatedBy);

        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);

        return MentorshipRequestMapperManual.toDto(mentorshipRequest);
    }

    @Override
    @Transactional
    public MentorshipRequestDTO approveOrReject(Long id, MentorshipRequestDTO dto) {
        log.info("Updating mentorship request with id: {}", id);

        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentorship request not found"));

        MentorshipRequest partialUpdate = MentorshipRequestMapperManual.toEntity(dto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidInputException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        log.debug("Current user: {}", currentUser.getUsername());
        if (dto.getStatus() != null) {
            MentorshipStatus newStatus =
                    MentorshipStatus.valueOf(dto.getStatus().toUpperCase());
            mentorshipRequest.setStatus(newStatus);

            if (newStatus == MentorshipStatus.APPROVED) {
                createMentorTeamIfNotExists(mentorshipRequest);
            }
        }

        if (dto.getHackathonId() != null) {
            Hackathon hackathon = hackathonRepository
                    .findById(Long.parseLong(dto.getHackathonId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
            mentorshipRequest.setHackathon(hackathon);
        }

        if (dto.getMentorId() != null) {
            User mentor = userRepository
                    .findById(Long.parseLong(dto.getMentorId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
            mentorshipRequest.setMentor(mentor);
        }

        if (dto.getTeamId() != null) {
            Team team = teamRepository
                    .findById(Long.parseLong(dto.getTeamId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            mentorshipRequest.setTeam(team);
        }

        //        if (dto.getEvaluatedById() != null) {
        //            User evaluatedBy = userRepository
        //                    .findById(Long.parseLong(dto.getEvaluatedById()))
        //                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //            mentorshipRequest.setEvaluatedBy(evaluatedBy);
        //        }
        mentorshipRequest.setEvaluatedAt(LocalDateTime.now());
        mentorshipRequest.setEvaluatedBy(currentUser);

        MentorshipRequest saved = mentorshipRequestRepository.save(mentorshipRequest);
        return MentorshipRequestMapperManual.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting mentorship request with id: {}", id);
        if (!mentorshipRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mentorship request not found");
        }
        mentorshipRequestRepository.deleteById(id);
    }

    @Override
    public List<MentorshipRequestDTO> getAll() {
        log.info("Fetching all mentorship requests");
        return mentorshipRequestRepository.findAll().stream()
                .map(MentorshipRequestMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MentorshipRequestDTO getById(Long id) {
        log.info("Fetching mentorship request with id: {}", id);
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentorship request not found"));
        return MentorshipRequestMapperManual.toDto(mentorshipRequest);
    }

    @Override
    public List<MentorshipRequestDTO> getAllByTeamIdAndHackathonId(String teamId, String hackathonId) {
        List<MentorshipRequest> requests = mentorshipRequestRepository.findAllByTeamIdAndHackathonId(
                Long.parseLong(teamId), Long.parseLong(hackathonId));
        return requests.stream().map(MentorshipRequestMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MentorshipRequestDTO> getAllByMentorId(String mentorId) {
        List<MentorshipRequest> requests = mentorshipRequestRepository.findAllByMentorId(Long.parseLong(mentorId));
        return requests.stream().map(MentorshipRequestMapperManual::toDto).collect(Collectors.toList());
    }

    private Long parseLongSafely(String value) {
        try {
            return value != null ? Long.parseLong(value) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format: " + value);
        }
    }

    private void createMentorTeamIfNotExists(MentorshipRequest mentorshipRequest) {
        boolean exists = mentorTeamRepository.existsByHackathonAndMentorAndTeam(
                mentorshipRequest.getHackathon(), mentorshipRequest.getMentor(), mentorshipRequest.getTeam());

        if (!exists) {
            MentorTeam mentorTeam = new MentorTeam();
            mentorTeam.setHackathon(mentorshipRequest.getHackathon());
            mentorTeam.setMentor(mentorshipRequest.getMentor());
            mentorTeam.setTeam(mentorshipRequest.getTeam());

            mentorTeamRepository.save(mentorTeam);
            log.info(
                    "Created MentorTeam for mentor: {}, team: {}",
                    mentorTeam.getMentor().getUsername(),
                    mentorTeam.getTeam().getName());
        } else {
            log.info("MentorTeam already exists for this combination. Skipped creation.");
        }
    }
}
