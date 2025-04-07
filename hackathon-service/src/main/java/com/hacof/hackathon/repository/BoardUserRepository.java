package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Board;
import com.hacof.hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    boolean existsByBoardAndUser(Board board, User user);
}
