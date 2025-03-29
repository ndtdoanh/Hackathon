package com.hacof.submission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.JudgeSubmission;

import feign.Param;

@Repository
public interface JudgeSubmissionRepository extends JpaRepository<JudgeSubmission, Long> {
    @Query("SELECT js FROM JudgeSubmission js JOIN js.submission s WHERE s.id = :submissionId")
    List<JudgeSubmission> findBySubmissionId(@Param("submissionId") Long submissionId);

    List<JudgeSubmission> findByJudgeId(Long judgeId);

    @Query("SELECT js FROM JudgeSubmission js WHERE js.id = :roundId")
    List<JudgeSubmission> findByRoundId(@Param("roundId") Long roundId);
}
