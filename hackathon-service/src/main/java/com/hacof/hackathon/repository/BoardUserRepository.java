package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {}
