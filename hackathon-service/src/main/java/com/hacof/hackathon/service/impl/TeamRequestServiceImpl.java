package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamMapper;
import com.hacof.hackathon.mapper.TeamRequestMapper;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.TeamRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamRequestServiceImpl implements TeamRequestService {
    private final TeamRequestRepository teamRequestRepository;
    private final TeamRequestMapper teamRequestMapper;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO teamRequestDTO) {
        TeamRequest teamRequest = teamRequestMapper.toEntity(teamRequestDTO);
        teamRequest.setStatus(Status.PENDING);
        return teamRequestMapper.toDto(teamRequestRepository.save(teamRequest));
    }

    @Override
    public TeamRequestDTO approveTeamRequest(long id, long userId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        teamRequest.setStatus(Status.APPROVED);
        teamRequest.setReviewedBy(user);
        teamRequest.setReviewedAt(LocalDateTime.now());
        return teamRequestMapper.toDto(teamRequestRepository.save(teamRequest));
    }

    @Override
    public TeamRequestDTO rejectTeamRequest(long id, long userId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        teamRequest.setStatus(Status.REJECTED);
        teamRequest.setReviewedBy(user);
        teamRequest.setReviewedAt(LocalDateTime.now());
        return teamRequestMapper.toDto(teamRequestRepository.save(teamRequest));
    }

    @Override
    public void sendRequestToMembers(long teamRequestId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));
        // Logic to send request to members (e.g., email, notification)
    }

    @Override
    public void confirmMemberRequest(long teamRequestId, long userId, String status) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));
        // Logic to confirm member request
    }

    @Override
    public TeamDTO createTeamFromRequest(long teamRequestId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Team request not found"));
        if (teamRequest.getStatus() != Status.APPROVED) {
            throw new IllegalStateException("Team request must be approved to create a team");
        }
        Team team = Team.builder()
                .name("Team for request " + teamRequestId)
                // .hackathon(teamRequest.getHackathon())
                .teamLeader(teamRequest.getReviewedBy())
                .build();
        return teamMapper.toDto(teamRepository.save(team));
    }
}
