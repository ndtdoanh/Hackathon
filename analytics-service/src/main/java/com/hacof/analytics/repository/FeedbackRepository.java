package com.hacof.analytics.repository;

import com.hacof.analytics.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    boolean existsByHackathonId(Long hackathonId);

    List<Feedback> findByTeamId(Long teamId);

    List<Feedback> findByHackathonId(Long hackathonId);

    List<Feedback> findByMentorId(Long mentorId);
}
