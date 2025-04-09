package com.hacof.communication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.BoardList;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
    Optional<BoardList> findByNameAndBoardId(String name, Long boardId);
}
