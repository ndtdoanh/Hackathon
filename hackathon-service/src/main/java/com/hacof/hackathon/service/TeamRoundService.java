package com.hacof.hackathon.service;

import org.springframework.data.domain.Page;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.dto.TeamRoundSearchDTO;

import java.util.List;

public interface TeamRoundService {
    TeamRoundDTO create(TeamRoundDTO teamRoundDTO);

    TeamRoundDTO update(String id, TeamRoundDTO teamRoundDTO);

    void delete(String id);

    Page<TeamRoundDTO> searchTeamRounds(TeamRoundSearchDTO searchDTO);

    List<TeamRoundDTO> getAllByRoundId(String roundId);

    List<TeamRoundDTO> getAllByJudgeIdAndRoundId(String judgeId, String roundId);

}
