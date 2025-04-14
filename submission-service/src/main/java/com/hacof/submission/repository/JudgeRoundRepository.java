package com.hacof.submission.repository;

import com.hacof.submission.entity.JudgeRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgeRoundRepository extends JpaRepository<JudgeRound, Long> {
    Optional<JudgeRound> findByJudgeId(Long judgeId);

    List<JudgeRound> findByRoundId(Long roundId);

    boolean existsByJudgeIdAndRoundId(Long judgeId, Long roundId);

    Optional<JudgeRound> findByJudgeIdAndRoundId(Long judgeId, Long roundId);
}
