package com.hacof.analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    boolean existsByHackathonId(Long hackathonId);

    List<Feedback> findByTeamId(Long teamId);

    List<Feedback> findByHackathonId(Long hackathonId);

    List<Feedback> findByMentorId(Long mentorId);

    List<Feedback> findByCreatedByUsername(String username);

    List<Feedback> findByCreatedByUsernameAndHackathonId(String username, Long hackathonId);
}
