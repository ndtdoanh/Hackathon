package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.MentorshipStatus;
import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.mapper.MentorshipRequestMapper;
import com.hacof.hackathon.repository.MentorshipRequestRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MentorshipRequestServiceImpl implements MentorshipRequestService {
    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestMapper mentorshipRequestMapper;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public MentorshipRequestDTO requestMentor(Long teamId, Long mentorId) {
        MentorshipRequest mentorshipRequest = new MentorshipRequest();
        mentorshipRequest.setTeam(
                teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Invalid team ID")));
        mentorshipRequest.setMentor(
                userRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        mentorshipRequest.setStatus(MentorshipStatus.PENDING);
        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }

    @Override
    public MentorshipRequestDTO approveRequest(Long requestId, Long mentorId) {
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        mentorshipRequest.setStatus(MentorshipStatus.APPROVED);
        mentorshipRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        mentorshipRequest.setEvaluatedAt(LocalDateTime.now());
        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }

    @Override
    public MentorshipRequestDTO rejectRequest(Long requestId, Long mentorId) {
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        mentorshipRequest.setStatus(MentorshipStatus.REJECTED);
        mentorshipRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        mentorshipRequest.setEvaluatedAt(LocalDateTime.now());
        mentorshipRequest = mentorshipRequestRepository.save(mentorshipRequest);
        return mentorshipRequestMapper.toDto(mentorshipRequest);
    }

    @Override
    public List<MentorshipRequestDTO> getRequestsByTeam(Long teamId) {
        return mentorshipRequestRepository.findByTeamId(teamId).stream()
                .map(mentorshipRequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
