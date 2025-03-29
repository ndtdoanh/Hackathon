package com.hacof.submission.service;

import com.hacof.submission.dto.request.TeamRoundJudgeRequestDTO;
import com.hacof.submission.dto.response.TeamRoundJudgeResponseDTO;

import java.util.List;

public interface TeamRoundJudgeService {

    TeamRoundJudgeResponseDTO createTeamRoundJudge(TeamRoundJudgeRequestDTO requestDTO);

    TeamRoundJudgeResponseDTO updateTeamRoundJudge(Long id, TeamRoundJudgeRequestDTO requestDTO);

    void deleteTeamRoundJudge(Long id);

    TeamRoundJudgeResponseDTO getTeamRoundJudgeById(Long id);

    List<TeamRoundJudgeResponseDTO> getAllTeamRoundJudges();
}
