package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.TeamRequestMember;

public interface TeamRequestMemberRepository extends JpaRepository<TeamRequestMember, Long> {}
