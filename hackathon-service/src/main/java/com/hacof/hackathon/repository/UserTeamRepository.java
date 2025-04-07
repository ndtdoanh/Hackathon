package com.hacof.hackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeam;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    boolean existsByUserAndTeam(User user, Team team);
}
