package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.UserHackathon;

public interface UserHackathonRepository extends JpaRepository<UserHackathon, Long> {}
