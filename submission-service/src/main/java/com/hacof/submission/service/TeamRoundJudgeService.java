package com.hacof.submission.service;

import java.util.List;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;

public interface TeamRoundJudgeService {

    TeamRoundJudgeResponseDTO createTeamRoundJudge(TeamRoundJudgeRequestDTO requestDTO);

    TeamRoundJudgeResponseDTO updateTeamRoundJudge(Long id, TeamRoundJudgeRequestDTO requestDTO);

    void deleteTeamRoundJudge(Long id);

    TeamRoundJudgeResponseDTO getTeamRoundJudgeById(Long id);

    List<TeamRoundJudgeResponseDTO> getAllTeamRoundJudges();

    List<TeamRoundJudgeResponseDTO> getTeamRoundJudgesByTeamRoundId(Long teamRoundId);
}
