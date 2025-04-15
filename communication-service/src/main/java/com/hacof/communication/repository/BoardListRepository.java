package com.hacof.communication.repository;

import com.hacof.communication.entity.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {
    Optional<BoardList> findByNameAndBoardId(String name, Long boardId);

    List<BoardList> findByBoardId(Long boardId);
}
