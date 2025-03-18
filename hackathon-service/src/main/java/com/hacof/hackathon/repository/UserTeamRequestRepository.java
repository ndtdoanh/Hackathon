package com.hacof.hackathon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeamRequest;

@Repository
public interface UserTeamRequestRepository extends JpaRepository<UserTeamRequest, Long> {
    boolean existsByTeamAndUserAndStatusAndRequestType(Team team, User user, Status status, String requestType);

    Optional<UserTeamRequest> findByTeamAndUserAndRequestType(Team team, User user, String requestType);

    List<UserTeamRequest> findByTeamAndStatus(Team team, Status status);

    List<UserTeamRequest> findByUserAndStatus(User user, Status status);
}
