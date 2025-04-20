package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TeamRoundDTO;

public interface TeamRoundService {
    TeamRoundDTO create(TeamRoundDTO teamRoundDTO);

    TeamRoundDTO update(String id, TeamRoundDTO teamRoundDTO);

    void delete(String id);

    List<TeamRoundDTO> getAllByRoundId(String roundId);

    List<TeamRoundDTO> getAllByJudgeIdAndRoundId(String judgeId, String roundId);

    List<TeamRoundDTO> updateBulk(List<TeamRoundDTO> teamRoundDTOs);
}
