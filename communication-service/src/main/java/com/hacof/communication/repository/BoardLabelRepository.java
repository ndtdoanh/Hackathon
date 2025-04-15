package com.hacof.communication.repository;

import com.hacof.communication.entity.BoardLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardLabelRepository extends JpaRepository<BoardLabel, Long> {

    Optional<BoardLabel> findByNameAndBoardId(String name, Long boardId);

    List<BoardLabel> findByBoardId(Long boardId);
}
