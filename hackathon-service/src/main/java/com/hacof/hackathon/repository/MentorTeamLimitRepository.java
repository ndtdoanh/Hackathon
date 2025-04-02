package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.MentorTeamLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorTeamLimitRepository extends JpaRepository<MentorTeamLimit, Long> {
}
