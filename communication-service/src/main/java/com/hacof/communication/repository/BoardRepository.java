package com.hacof.communication.repository;

import com.hacof.communication.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByNameAndTeamId(String name, Long teamId);

    List<Board> findByTeamIdAndHackathonId(Long teamId, Long hackathonId);
}
