package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamRoundJudgeDTO;

public interface TeamRoundJudgeService {
    TeamRoundJudgeDTO create(TeamRoundJudgeDTO dto);

    TeamRoundJudgeDTO update(Long id, TeamRoundJudgeDTO dto);

    void delete(Long id);

    List<TeamRoundJudgeDTO> getAll();

    TeamRoundJudgeDTO getById(Long id);
}
