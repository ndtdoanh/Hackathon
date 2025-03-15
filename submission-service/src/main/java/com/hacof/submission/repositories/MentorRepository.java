package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {}
