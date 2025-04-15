package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.MentorshipSessionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorshipSessionRequestRepository extends JpaRepository<MentorshipSessionRequest, Long> {
    List<MentorshipSessionRequest> findAllByMentorTeamId(Long mentorTeamId);
}
