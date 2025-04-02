package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.MentorshipRequestMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.MentorshipRequestRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipRequestService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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

        User evaluatedBy = userRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getEvaluatedById()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MentorshipRequest mentorshipRequest = mentorshipRequestMapper.toEntity(mentorshipRequestDTO);
        mentorshipRequest.setHackathon(hackathon);
        mentorshipRequest.setMentor(mentor);
        mentorshipRequest.setTeam(team);
        mentorshipRequest.setEvaluatedBy(evaluatedBy);

        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }

    @Override
    public MentorshipRequestDTO update(Long id, MentorshipRequestDTO mentorshipRequestDTO) {
        log.info("Updating mentorship request with id: {}", id);

        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentorship request not found"));

        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getHackathonId()))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));

        User mentor = userRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getMentorId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        Team team = teamRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getTeamId()))
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User evaluatedBy = userRepository
                .findById(Long.parseLong(mentorshipRequestDTO.getEvaluatedById()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        mentorshipRequestMapper.updateEntityFromDto(mentorshipRequestDTO, mentorshipRequest);
        mentorshipRequest.setHackathon(hackathon);
        mentorshipRequest.setMentor(mentor);
        mentorshipRequest.setTeam(team);
        mentorshipRequest.setEvaluatedBy(evaluatedBy);

        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);
        return mentorshipRequestMapper.toDto(mentorshipRequest);
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
        if (userRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No mentorship requests found");
        }
        return mentorshipRequestRepository.findAll().stream()
                .map(mentorshipRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MentorshipRequestDTO getById(Long id) {
        log.info("Fetching mentorship request with id: {}", id);
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mentorship request not found"));
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }
}
