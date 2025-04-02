package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.dto.TeamRoundSearchDTO;

public interface TeamRoundService {
    TeamRoundDTO create(TeamRoundDTO teamRoundDTO);

    TeamRoundDTO update(String id, TeamRoundDTO teamRoundDTO);

    void delete(String id);

    Page<TeamRoundDTO> searchTeamRounds(TeamRoundSearchDTO searchDTO);

    List<TeamRoundDTO> getAllByRoundId(String roundId);

    List<TeamRoundDTO> getAllByJudgeIdAndRoundId(String judgeId, String roundId);
}
