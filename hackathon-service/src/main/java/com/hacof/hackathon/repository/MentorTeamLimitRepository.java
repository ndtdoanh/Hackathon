package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.MentorTeamLimit;

@Repository
public interface MentorTeamLimitRepository extends JpaRepository<MentorTeamLimit, Long> {}
