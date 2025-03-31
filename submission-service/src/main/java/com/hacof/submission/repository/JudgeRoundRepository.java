package com.hacof.submission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.JudgeRound;

@Repository
public interface JudgeRoundRepository extends JpaRepository<JudgeRound, Long> {
    Optional<JudgeRound> findByJudgeId(Long judgeId);

    List<JudgeRound> findByRoundId(Long roundId);

    boolean existsByJudgeIdAndRoundId(Long judgeId, Long roundId);
}
