package com.hacof.hackathon.service.impl;

import static com.hacof.hackathon.constant.MentorshipStatus.COMPLETED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class MentorshipRequestServiceImpl implements MentorshipRequestService {

    @Autowired
    private MentorshipRequestRepository mentorshipRequestRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorshipRequestMapper mentorshipRequestMapper;

    @Override
    public MentorshipRequestDTO requestMentor(Long teamId, Long mentorId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User mentor =
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));

        MentorshipRequest request = new MentorshipRequest();
        request.setTeam(team);
        request.setMentor(mentor);
        request.setStatus(COMPLETED);

        request = mentorshipRequestRepository.save(request);
        return mentorshipRequestMapper.toDTO(request);
    }

    @Override
    public List<MentorshipRequestDTO> getRequestsByTeam(Long teamId) {
        return null;
    }
}
