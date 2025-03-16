package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.MentorshipRequest;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {}
