package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
    List<MentorshipRequest> findAllByTeamIdAndHackathonId(Long teamId, Long hackathonId);

    List<MentorshipRequest> findAllByMentorId(Long mentorId);
}
