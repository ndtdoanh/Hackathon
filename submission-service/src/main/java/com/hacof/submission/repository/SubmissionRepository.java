package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {}
