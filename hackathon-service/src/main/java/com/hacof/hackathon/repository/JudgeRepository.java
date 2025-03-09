package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Judge;

public interface JudgeRepository extends JpaRepository<Judge, Long> {}
