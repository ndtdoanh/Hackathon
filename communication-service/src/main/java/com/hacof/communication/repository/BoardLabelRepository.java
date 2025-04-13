package com.hacof.communication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.BoardLabel;

@Repository
public interface BoardLabelRepository extends JpaRepository<BoardLabel, Long> {

    Optional<BoardLabel> findByNameAndBoardId(String name, Long boardId);

    List<BoardLabel> findByBoardId(Long boardId);
}
