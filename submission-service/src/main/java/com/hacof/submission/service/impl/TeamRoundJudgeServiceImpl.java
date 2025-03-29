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

    @Override
    public TeamRoundJudgeResponseDTO updateTeamRoundJudge(Long id, TeamRoundJudgeRequestDTO requestDTO) {
        // Find the existing TeamRoundJudge entity by ID
        Optional<TeamRoundJudge> teamRoundJudgeOptional = teamRoundJudgeRepository.findById(id);
        if (!teamRoundJudgeOptional.isPresent()) {
            throw new IllegalArgumentException("TeamRoundJudge not found with ID " + id);
        }

        // Get the existing TeamRoundJudge entity
        TeamRoundJudge teamRoundJudge = teamRoundJudgeOptional.get();

        // Update the entity using the provided request DTO
        teamRoundJudgeMapper.updateEntityFromDTO(requestDTO, teamRoundJudge);

        // Save the updated entity back to the repository
        teamRoundJudge = teamRoundJudgeRepository.save(teamRoundJudge);

        // Return the updated response DTO
        return teamRoundJudgeMapper.toResponseDTO(teamRoundJudge);
    }

    @Override
    public void deleteTeamRoundJudge(Long id) {
        // Check if the TeamRoundJudge exists
        if (!teamRoundJudgeRepository.existsById(id)) {
            throw new IllegalArgumentException("TeamRoundJudge not found with ID " + id);
        }
        // Delete the TeamRoundJudge by ID
        teamRoundJudgeRepository.deleteById(id);
    }

    @Override
    public TeamRoundJudgeResponseDTO getTeamRoundJudgeById(Long id) {
        TeamRoundJudge entity = teamRoundJudgeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TeamRoundJudge not found with ID " + id));
        return teamRoundJudgeMapper.toResponseDTO(entity); // Map to response DTO after finding the entity
    }

}
