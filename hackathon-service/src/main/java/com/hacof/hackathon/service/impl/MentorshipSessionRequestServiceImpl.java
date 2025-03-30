package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.MentorshipSessionRequestDTO;
import com.hacof.hackathon.entity.MentorshipSessionRequest;
import com.hacof.hackathon.mapper.MentorshipSessionRequestMapper;
import com.hacof.hackathon.repository.MentorshipSessionRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorshipSessionRequestService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MentorshipSessionRequestServiceImpl implements MentorshipSessionRequestService {
    private final MentorshipSessionRequestRepository mentorshipSessionRequestRepository;
    private final MentorshipSessionRequestMapper mentorshipSessionRequestMapper;
    private final UserRepository userRepository;

    @Override
    public MentorshipSessionRequestDTO requestSession(
            String mentorTeamId, LocalDateTime startTime, LocalDateTime endTime, String location, String description) {
        //        MentorshipSessionRequest sessionRequest = new MentorshipSessionRequest();
        //        sessionRequest.setMentor(userRepository
        //                .findById(Long.parseLong(mentorTeamId))
        //                .orElseThrow(() -> new IllegalArgumentException("Invalid mentor team ID")));
        //        sessionRequest.setStartTime(startTime);
        //        sessionRequest.setEndTime(endTime);
        //        sessionRequest.setLocation(location);
        //        sessionRequest.setDescription(description);
        //        sessionRequest.setStatus(Status.PENDING);
        //        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        //        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
        return null;
    }

    @Override
    public MentorshipSessionRequestDTO approveSession(Long sessionId, Long mentorId) {
        MentorshipSessionRequest sessionRequest = mentorshipSessionRequestRepository
                .findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid session ID"));
        sessionRequest.setStatus(Status.APPROVED);
        sessionRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        sessionRequest.setEvaluatedAt(LocalDateTime.now());
        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
    }

    @Override
    public MentorshipSessionRequestDTO rejectSession(Long sessionId, Long mentorId) {
        MentorshipSessionRequest sessionRequest = mentorshipSessionRequestRepository
                .findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid session ID"));
        sessionRequest.setStatus(Status.REJECTED);
        sessionRequest.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID")));
        sessionRequest.setEvaluatedAt(LocalDateTime.now());
        sessionRequest = mentorshipSessionRequestRepository.save(sessionRequest);
        return mentorshipSessionRequestMapper.toDTO(sessionRequest);
    }
}
