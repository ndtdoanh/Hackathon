package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;

public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {
    List<IndividualRegistrationRequest> findAllByStatus(IndividualRegistrationRequestStatus status);

    List<IndividualRegistrationRequest> findAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequest> findAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonId(Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonIdAndStatus(
            Long hackathonId, IndividualRegistrationRequestStatus status);
}
