package com.hacof.hackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.Team;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByTeam(Team team);

    Optional<Board> findByTeamId(Long teamId);
}
