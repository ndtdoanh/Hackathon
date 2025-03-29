package com.hacof.submission.mapper;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;
import com.hacof.submission.dto.response.TeamRoundResponseDTO;
import com.hacof.submission.dto.response.UserResponse;  // Sửa ở đây
import com.hacof.submission.entity.TeamRoundJudge;
import com.hacof.submission.entity.TeamRound;
import com.hacof.submission.entity.User;
import com.hacof.submission.repository.TeamRoundRepository;
import com.hacof.submission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamRoundJudgeMapper {
    @Autowired
    private TeamRoundRepository teamRoundRepository;

    @Autowired
    private UserRepository userRepository;

    // Converts TeamRoundJudgeRequestDTO to TeamRoundJudge entity
    public TeamRoundJudge toEntity(TeamRoundJudgeRequestDTO requestDTO, TeamRoundRepository teamRoundRepository, UserRepository userRepository) {
        // Fetch the TeamRound and Judge entities using their respective IDs from the DTO
        TeamRound teamRound = teamRoundRepository.findById(requestDTO.getTeamRoundId())
                .orElseThrow(() -> new IllegalArgumentException("TeamRound not found with ID " + requestDTO.getTeamRoundId()));

        User judge = userRepository.findById(requestDTO.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + requestDTO.getJudgeId()));

        // Create and return the TeamRoundJudge entity
        return TeamRoundJudge.builder()
                .teamRound(teamRound)
                .judge(judge)
                .build();
    }

    // Chuyển từ TeamRoundJudge entity sang TeamRoundJudgeResponseDTO
    public TeamRoundJudgeResponseDTO toResponseDTO(TeamRoundJudge entity) {
        TeamRoundJudgeResponseDTO responseDTO = new TeamRoundJudgeResponseDTO();

        // Set TeamRoundJudge details
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        responseDTO.setCreatedDate(entity.getCreatedDate() != null ? entity.getCreatedDate().toString() : null);
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate() != null ? entity.getLastModifiedDate().toString() : null);

        // Map TeamRound and User (Judge) to responseDTO
        responseDTO.setTeamRound(mapTeamRoundToDto(entity.getTeamRound()));
        responseDTO.setJudge(mapUserToDto(entity.getJudge()));
        return responseDTO;
    }

    // Map TeamRound entity to TeamRoundResponseDTO
    private TeamRoundResponseDTO mapTeamRoundToDto(TeamRound teamRound) {
        TeamRoundResponseDTO teamRoundResponseDTO = new TeamRoundResponseDTO();
        teamRoundResponseDTO.setId(teamRound.getId());
        teamRoundResponseDTO.setTeamId(teamRound.getTeam().getId());
        teamRoundResponseDTO.setRoundId(teamRound.getRound().getId());
        teamRoundResponseDTO.setStatus(teamRound.getStatus());
        teamRoundResponseDTO.setDescription(teamRound.getDescription());
        teamRoundResponseDTO.setCreatedDate(teamRound.getCreatedDate().toString());
        teamRoundResponseDTO.setLastModifiedDate(teamRound.getLastModifiedDate().toString());
        return teamRoundResponseDTO;
    }

    // Map User entity (Judge) to UserResponse
    private UserResponse mapUserToDto(User judge) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(judge.getId());
        userResponse.setUsername(judge.getUsername());
        userResponse.setEmail(judge.getEmail());
        userResponse.setFirstName(judge.getFirstName());
        userResponse.setLastName(judge.getLastName());
        userResponse.setCreatedDate(judge.getCreatedDate());
        userResponse.setLastModifiedDate(judge.getLastModifiedDate());
        return userResponse;
    }

    public void updateEntityFromDTO(TeamRoundJudgeRequestDTO dto, TeamRoundJudge entity) {
        // Fetch and set TeamRound and Judge for the existing entity
        TeamRound teamRound = teamRoundRepository.findById(dto.getTeamRoundId())
                .orElseThrow(() -> new IllegalArgumentException("TeamRound not found"));
        User judge = userRepository.findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));

        // Update fields in the entity
        entity.setTeamRound(teamRound);
        entity.setJudge(judge);
    }
}
