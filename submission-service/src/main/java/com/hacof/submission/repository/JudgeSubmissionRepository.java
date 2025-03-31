package com.hacof.submission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.User;

import feign.Param;

@Repository
public interface JudgeSubmissionRepository extends JpaRepository<JudgeSubmission, Long> {
    boolean existsByJudgeIdAndSubmissionId(Long judgeId, Long submissionId);

    List<JudgeSubmission> findByJudgeId(Long judgeId);

    @Query("SELECT js FROM JudgeSubmission js WHERE js.id = :roundId")
    List<JudgeSubmission> findByRoundId(@Param("roundId") Long roundId);

    @Query("SELECT COALESCE(SUM(js.score), 0) FROM JudgeSubmission js WHERE js.submission.id = :submissionId")
    int getTotalScoreBySubmission(@Param("submissionId") Long submissionId);

    @Query("SELECT js.judge FROM JudgeSubmission js WHERE js.submission.id = :submissionId")
    List<User> findJudgesWhoCompletedSubmission(@Param("submissionId") Long submissionId);
}
