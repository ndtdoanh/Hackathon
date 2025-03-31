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

    public TeamRoundJudge toEntity(TeamRoundJudgeRequestDTO requestDTO, TeamRoundRepository teamRoundRepository, UserRepository userRepository) {
        TeamRound teamRound = teamRoundRepository.findById(requestDTO.getTeamRoundId())
                .orElseThrow(() -> new IllegalArgumentException("TeamRound not found with ID " + requestDTO.getTeamRoundId()));

        User judge = userRepository.findById(requestDTO.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + requestDTO.getJudgeId()));

        return TeamRoundJudge.builder()
                .teamRound(teamRound)
                .judge(judge)
                .build();
    }

    public TeamRoundJudgeResponseDTO toResponseDTO(TeamRoundJudge entity) {
        TeamRoundJudgeResponseDTO responseDTO = new TeamRoundJudgeResponseDTO();
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setCreatedByUserName(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null);
        responseDTO.setCreatedAt(entity.getCreatedDate() != null ? entity.getCreatedDate().toString() : null);
        responseDTO.setUpdatedAt(entity.getLastModifiedDate() != null ? entity.getLastModifiedDate().toString() : null);

        responseDTO.setTeamRound(mapTeamRoundToDto(entity.getTeamRound()));
        responseDTO.setJudge(mapUserToDto(entity.getJudge()));
        return responseDTO;
    }

    private TeamRoundResponseDTO mapTeamRoundToDto(TeamRound teamRound) {
        TeamRoundResponseDTO teamRoundResponseDTO = new TeamRoundResponseDTO();
        teamRoundResponseDTO.setId(String.valueOf(teamRound.getId()));
        teamRoundResponseDTO.setTeamId(String.valueOf(teamRound.getTeam().getId()));
        teamRoundResponseDTO.setRoundId(String.valueOf(teamRound.getRound().getId()));
        teamRoundResponseDTO.setStatus(teamRound.getStatus());
        teamRoundResponseDTO.setDescription(teamRound.getDescription());
        teamRoundResponseDTO.setCreatedAt(teamRound.getCreatedDate().toString());
        teamRoundResponseDTO.setUpdatedAt(teamRound.getLastModifiedDate().toString());
        return teamRoundResponseDTO;
    }

    private UserResponse mapUserToDto(User judge) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(judge.getId()));
        userResponse.setUsername(judge.getUsername());
        userResponse.setEmail(judge.getEmail());
        userResponse.setFirstName(judge.getFirstName());
        userResponse.setLastName(judge.getLastName());
        return userResponse;
    }

    public void updateEntityFromDTO(TeamRoundJudgeRequestDTO dto, TeamRoundJudge entity) {
        TeamRound teamRound = teamRoundRepository.findById(dto.getTeamRoundId())
                .orElseThrow(() -> new IllegalArgumentException("TeamRound not found"));
        User judge = userRepository.findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));

        entity.setTeamRound(teamRound);
        entity.setJudge(judge);
    }
}
