package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByTeam(Team team);
    Optional<Board> findByTeamId(Long teamId);
}
