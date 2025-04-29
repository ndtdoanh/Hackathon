package com.hacof.submission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.TeamRoundJudge;
import com.hacof.submission.entity.User;

@Repository
public interface TeamRoundJudgeRepository extends JpaRepository<TeamRoundJudge, Long> {
    @Query(
            "SELECT COUNT(trj) > 0 FROM TeamRoundJudge trj WHERE trj.judge.id = :judgeId AND trj.teamRound.round.id = :roundId AND trj.teamRound.team.id = :teamId")
    boolean existsByJudgeAndRoundAndTeam(
            @Param("judgeId") Long judgeId, @Param("roundId") Long roundId, @Param("teamId") Long teamId);

    @Query(
            "SELECT trj.judge FROM TeamRoundJudge trj WHERE trj.teamRound.round.id = :roundId AND trj.teamRound.team.id = :teamId")
    List<User> findJudgesByRoundAndTeam(@Param("roundId") Long roundId, @Param("teamId") Long teamId);

    List<TeamRoundJudge> findByTeamRoundId(Long teamRoundId);

    Optional<TeamRoundJudge> findByTeamRoundIdAndJudgeId(Long teamRoundId, Long judgeId);

    boolean existsByTeamRoundIdAndJudgeId(Long teamRoundId, Long judgeId);
}
