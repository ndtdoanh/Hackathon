package com.hacof.submission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByRoundIdAndCreatedByUsername(Long roundId, String createdByUsername);

    List<Submission> findByTeamIdAndRoundId(Long teamId, Long roundId);

    List<Submission> findByRoundId(Long roundId);
}
