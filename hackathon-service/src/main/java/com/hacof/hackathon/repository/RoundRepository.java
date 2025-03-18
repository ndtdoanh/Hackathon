package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Round;

public interface RoundRepository extends JpaRepository<Round, Long>, JpaSpecificationExecutor<Round> {}
