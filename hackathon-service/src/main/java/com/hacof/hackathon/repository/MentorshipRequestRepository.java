package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorshipRequest;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
    List<MentorshipRequest> findAllByTeamIdAndHackathonId(Long teamId, Long hackathonId);

    List<MentorshipRequest> findAllByMentorId(Long mentorId);

    boolean existsByHackathonAndMentorAndTeam(Hackathon hackathon, User mentor, Team team);
}
