package com.hacof.hackathon.repository;

import java.util.List;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;

import feign.Param;

public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {
    List<IndividualRegistrationRequest> findAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequest> findAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonId(Long hackathonId);

    @Query("SELECT r FROM IndividualRegistrationRequest r WHERE r.hackathon.id = :hackathonId AND r.status = :status")
    List<IndividualRegistrationRequest> findAllByHackathonIdAndStatus(
            @Param("hackathonId") Long hackathonId, @Param("status") IndividualRegistrationRequestStatus status);

    boolean existsByHackathonAndCreatedBy(Hackathon hackathon, User createdBy);

}
