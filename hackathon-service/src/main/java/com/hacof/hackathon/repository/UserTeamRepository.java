package com.hacof.hackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeam;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    Optional<UserTeam> findByTeamIdAndUserId(Long teamId, Long userId);

    long countByTeam(Team team);

    boolean existsByTeamAndUser(Team team, User user);
}
