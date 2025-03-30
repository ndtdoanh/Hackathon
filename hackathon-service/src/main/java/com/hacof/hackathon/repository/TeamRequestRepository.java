package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.TeamRequest;

@Repository
public interface TeamRequestRepository
        extends JpaRepository<TeamRequest, Long>, JpaSpecificationExecutor<TeamRequest> {}
