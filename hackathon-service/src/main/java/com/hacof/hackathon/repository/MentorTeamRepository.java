package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.MentorTeam;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

public interface MentorTeamRepository extends JpaRepository<MentorTeam, Long> {
    List<MentorTeam> findAllByHackathonIdAndTeamId(Long hackathonId, Long teamId);

    List<MentorTeam> findAllByMentorId(Long mentorId);

    boolean existsByHackathonAndMentorAndTeam(Hackathon hackathon, User mentor, Team team);
}
