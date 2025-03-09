package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {}
