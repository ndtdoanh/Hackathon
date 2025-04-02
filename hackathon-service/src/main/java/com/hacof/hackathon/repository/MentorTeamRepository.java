package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.MentorTeam;

@Repository
public interface MentorTeamRepository extends JpaRepository<MentorTeam, Long> {
    List<MentorTeam> findAllByHackathonIdAndTeamId(Long hackathonId, Long teamId);

    List<MentorTeam> findAllByMentorId(Long mentorId);
}
