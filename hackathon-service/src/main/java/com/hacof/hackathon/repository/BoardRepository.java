package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {}
