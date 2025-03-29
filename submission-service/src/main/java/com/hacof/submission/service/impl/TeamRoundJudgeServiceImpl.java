package com.hacof.submission.service.impl;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.entity.TeamRoundJudge;
import com.hacof.submission.mapper.TeamRoundJudgeMapper;
import com.hacof.submission.repository.TeamRoundJudgeRepository;
import com.hacof.submission.repository.TeamRoundRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.TeamRoundJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamRoundJudgeServiceImpl implements TeamRoundJudgeService {

    @Autowired
    private TeamRoundJudgeRepository teamRoundJudgeRepository;

    @Autowired
    private TeamRoundRepository teamRoundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRoundJudgeMapper teamRoundJudgeMapper;

    @Override
    public TeamRoundJudgeResponseDTO createTeamRoundJudge(TeamRoundJudgeRequestDTO requestDTO) {
        TeamRoundJudge entity = teamRoundJudgeMapper.toEntity(requestDTO, teamRoundRepository, userRepository);
        TeamRoundJudge savedEntity = teamRoundJudgeRepository.save(entity);
        return teamRoundJudgeMapper.toResponseDTO(savedEntity);
    }

}
