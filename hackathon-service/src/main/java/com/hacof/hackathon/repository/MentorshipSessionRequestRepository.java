package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.MentorshipSessionRequest;

public interface MentorshipSessionRequestRepository extends JpaRepository<MentorshipSessionRequest, Long> {
    List<MentorshipSessionRequest> findByMentorshipRequestId(Long mentorshipRequestId);
}
