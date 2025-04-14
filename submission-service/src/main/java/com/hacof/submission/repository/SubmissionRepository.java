package com.hacof.submission.repository;

import com.hacof.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByRoundIdAndCreatedByUsername(Long roundId, String createdByUsername);

    List<Submission> findByTeamIdAndRoundId(Long teamId, Long roundId);
}
