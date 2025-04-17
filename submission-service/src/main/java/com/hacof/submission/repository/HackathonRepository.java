package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {}
