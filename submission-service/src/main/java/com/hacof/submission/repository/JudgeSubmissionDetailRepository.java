package com.hacof.submission.repository;

import com.hacof.submission.entity.JudgeSubmissionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeSubmissionDetailRepository extends JpaRepository<JudgeSubmissionDetail, Long> {
    List<JudgeSubmissionDetail> findByJudgeSubmissionId(Long judgeSubmissionId);
}
