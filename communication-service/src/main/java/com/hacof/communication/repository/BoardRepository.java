package com.hacof.communication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByNameAndTeamId(String name, Long teamId);

    List<Board> findByTeamIdAndHackathonId(Long teamId, Long hackathonId);

    List<Board> findByTeamIsNullAndHackathonId(Long hackathonId);

    List<Board> findByTeamIsNullAndHackathonIsNull();
}
