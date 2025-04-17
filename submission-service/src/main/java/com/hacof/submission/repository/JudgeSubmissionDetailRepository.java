package com.hacof.submission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.JudgeSubmissionDetail;

@Repository
public interface JudgeSubmissionDetailRepository extends JpaRepository<JudgeSubmissionDetail, Long> {
    List<JudgeSubmissionDetail> findByJudgeSubmissionId(Long judgeSubmissionId);
}
