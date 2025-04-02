package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.IndividualRegistrationRequest;

import java.util.List;

@Repository
public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {
    List<IndividualRegistrationRequest> findAllByCreatedByUsername(String createdByUsername);
    List<IndividualRegistrationRequest> findAllByCreatedByUsernameAndHackathonId(String createdByUsername, Long hackathonId);

}
