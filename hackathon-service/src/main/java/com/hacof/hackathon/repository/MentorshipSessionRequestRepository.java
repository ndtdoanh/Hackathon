package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.MentorshipSessionRequest;

import java.util.List;

@Repository
public interface MentorshipSessionRequestRepository extends JpaRepository<MentorshipSessionRequest, Long> {
    List<MentorshipSessionRequest> findAllByMentorTeamId(Long mentorTeamId);

}
