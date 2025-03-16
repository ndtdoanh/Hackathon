package com.hacof.submission.repository;

import com.hacof.submission.entity.JudgeSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeSubmissionRepository extends JpaRepository<JudgeSubmission, Long> {
}
