package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {}
