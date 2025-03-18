package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.MentorshipStatus;
import com.hacof.hackathon.dto.MentorshipRequestDTO;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
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
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final MentorshipRequestMapper mentorshipRequestMapper;

    @Override
    public MentorshipRequestDTO requestMentor(Long teamId, Long mentorId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User mentor =
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        MentorshipRequest request = new MentorshipRequest();
        request.setTeam(team);
        request.setMentor(mentor);
        request.setStatus(MentorshipStatus.PENDING);

        request = mentorshipRequestRepository.save(request);
        return mentorshipRequestMapper.toDTO(request);
    }

    @Override
    public MentorshipRequestDTO approveRequest(Long requestId, Long mentorId) {
        MentorshipRequest request = mentorshipRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setStatus(MentorshipStatus.APPROVED);
        request.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        request = mentorshipRequestRepository.save(request);
        return mentorshipRequestMapper.toDTO(request);
    }

    @Override
    public MentorshipRequestDTO rejectRequest(Long requestId, Long mentorId) {
        MentorshipRequest request = mentorshipRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setStatus(MentorshipStatus.REJECTED);
        request.setEvaluatedBy(
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found")));
        request = mentorshipRequestRepository.save(request);
        return mentorshipRequestMapper.toDTO(request);
    }

    @Override
    public List<MentorshipRequestDTO> getRequestsByTeam(Long teamId) {
        List<MentorshipRequest> requests = mentorshipRequestRepository.findByTeamId(teamId);
        return requests.stream().map(mentorshipRequestMapper::toDTO).collect(Collectors.toList());
    }
}
