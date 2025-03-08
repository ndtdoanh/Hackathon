package com.hacof.submission.repositories;

import com.hacof.submission.entities.Submissionfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionfileRepository extends JpaRepository<Submissionfile, Long> {
}
