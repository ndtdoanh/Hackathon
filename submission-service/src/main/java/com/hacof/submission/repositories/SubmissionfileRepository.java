package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.Submissionfile;

@Repository
public interface SubmissionfileRepository extends JpaRepository<Submissionfile, Long> {}
