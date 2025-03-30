package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.TeamRound;

@Repository
public interface TeamRoundRepository extends JpaRepository<TeamRound, Long>, JpaSpecificationExecutor<TeamRound> {
    boolean existsByTeamIdAndRoundId(Long teamId, Long roundId);

    List<TeamRound> findByTeamId(Long teamId);

    List<TeamRound> findByRoundId(Long roundId);
}
