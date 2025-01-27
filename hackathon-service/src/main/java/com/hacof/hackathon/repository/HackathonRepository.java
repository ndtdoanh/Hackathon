package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Hackathon;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {}
