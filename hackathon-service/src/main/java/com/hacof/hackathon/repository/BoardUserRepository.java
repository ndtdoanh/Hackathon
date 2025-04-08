package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.BoardUser;
import com.hacof.hackathon.entity.User;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    boolean existsByBoardAndUser(Board board, User user);
}
