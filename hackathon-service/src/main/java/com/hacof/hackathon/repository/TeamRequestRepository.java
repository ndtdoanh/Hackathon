package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.User;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequest, Long> {
    boolean existsByLeaderAndHackathonAndStatus(User leader, Hackathon hackathon, Status status);
}
