package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.MentorRoundDTO;
import com.hacof.hackathon.entity.MentorRound;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.MentorRoundMapper;
import com.hacof.hackathon.repository.MentorRoundRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.MentorRoundService;

@Service
public class MentorRoundServiceImpl implements MentorRoundService {

    @Autowired
    private MentorRoundRepository mentorRoundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MentorRoundMapper mentorRoundMapper;

    @Override
    public MentorRoundDTO assignMentorToRound(Long mentorId, Long roundId) {
        User mentor =
                userRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        Round round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        MentorRound mentorRound = new MentorRound();
        mentorRound.setMentor(mentor);
        mentorRound.setRound(round);

        mentorRound = mentorRoundRepository.save(mentorRound);
        return mentorRoundMapper.toDTO(mentorRound);
    }

    @Override
    public List<MentorRoundDTO> getMentorsByRound(Long roundId) {
        return null;
    }
}
