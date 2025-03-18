package com.hacof.submission.repository;

import com.hacof.submission.entity.JudgeSubmission;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgeSubmissionRepository extends JpaRepository<JudgeSubmission, Long> {
    Optional<JudgeSubmission> findBySubmissionId(Long submissionId);

    List<JudgeSubmission> findByJudgeId(Long judgeId);

    @Query("SELECT js FROM JudgeSubmission js WHERE js.id = :roundId")
    List<JudgeSubmission> findByRoundId(@Param("roundId") Long roundId);}
