package com.hacof.analytics.repository;

import java.util.List;
import java.util.Optional;

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

    boolean existsByMentorId(Long mentorId);

    Optional<Feedback> findByHackathonIdAndMentorId(Long hackathonId, Long mentorId);

    Optional<Feedback> findByHackathon_Id(Long hackathonId);

    Optional<Feedback> findByMentor_Id(Long mentorId);

    boolean existsByHackathonIdAndMentorId(Long hackathonId, Long mentorId);

    boolean existsByHackathonIdAndMentorIsNull(Long hackathonId);
}
