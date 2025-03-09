package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {}
