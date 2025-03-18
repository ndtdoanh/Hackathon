package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.MentorshipSessionRequest;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.MentorshipSessionRequestMapper;
import com.hacof.hackathon.repository.MentorshipRequestRepository;
import com.hacof.hackathon.repository.MentorshipSessionRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipSessionRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MentorshipSessionRequestServiceImpl implements MentorshipSessionRequestService {
    private final MentorshipSessionRequestRepository mentorshipSessionRequestRepository;
    private final MentorshipSessionRequestMapper mentorshipSessionRequestMapper;
    private final UserRepository userRepository;
    private final MentorshipRequestRepository mentorshipRequestRepository;

    @Override
    public MentorshipSessionRequestDTO createSessionRequest(Long mentorshipRequestId, String sessionDetails) {
        MentorshipRequest mentorshipRequest = mentorshipRequestRepository
                .findById(mentorshipRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentorship request not found"));

        MentorshipSessionRequest sessionRequest = new MentorshipSessionRequest();
        sessionRequest.setMentorshipRequest(mentorshipRequest);
        sessionRequest.setSessionDetails(sessionDetails);
        sessionRequest.setStatus(Status.PENDING);

        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
    }

    @Override
    public MentorshipSessionRequestDTO approveSession(Long sessionId, Long mentorId) {
        MentorshipSessionRequest sessionRequest = mentorshipSessionRequestRepository
                .findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session request not found"));
        sessionRequest.setStatus(Status.APPROVED);
        sessionRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
    }

    @Override
    public MentorshipSessionRequestDTO rejectSession(Long sessionId, Long mentorId) {
        MentorshipSessionRequest sessionRequest = mentorshipSessionRequestRepository
                .findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session request not found"));
        sessionRequest.setStatus(Status.REJECTED);
        sessionRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
    }

    @Override
    public List<MentorshipSessionRequestDTO> getSessionsByMentorshipRequest(Long mentorshipRequestId) {
        List<MentorshipSessionRequest> sessions =
                mentorshipSessionRequestRepository.findByMentorshipRequestId(mentorshipRequestId);
        return sessions.stream().map(mentorshipSessionRequestMapper::toDTO).collect(Collectors.toList());
    }
}
