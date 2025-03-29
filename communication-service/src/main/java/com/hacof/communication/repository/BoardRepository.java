package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {}
