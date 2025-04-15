package com.hacof.communication.repository;

import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardUser;
import com.hacof.communication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

    List<BoardUser> findByBoardId(Long boardId);

    List<BoardUser> findByUserId(Long userId);

    boolean existsByBoardAndUser(Board board, User user);

    BoardUser findByBoardIdAndUserId(Long boardId, Long userId);
}
