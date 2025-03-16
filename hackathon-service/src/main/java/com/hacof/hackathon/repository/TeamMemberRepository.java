package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {}
