package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.TeamRound;

import feign.Param;

@Repository
public interface TeamRoundRepository extends JpaRepository<TeamRound, Long>, JpaSpecificationExecutor<TeamRound> {
    boolean existsByTeamIdAndRoundId(Long teamId, Long roundId);

    List<TeamRound> findAllByRoundId(Long roundId);

    @Query("SELECT tr FROM TeamRound tr JOIN tr.teamRoundJudges trj "
            + "WHERE tr.round.id = :roundId AND trj.judge.id = :judgeId")
    List<TeamRound> findAllByRoundIdAndJudgeId(@Param("roundId") Long roundId, @Param("judgeId") Long judgeId);
}
