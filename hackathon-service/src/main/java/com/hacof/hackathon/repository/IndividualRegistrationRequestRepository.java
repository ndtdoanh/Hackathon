package com.hacof.hackathon.repository;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {
    List<IndividualRegistrationRequest> findAllByStatus(IndividualRegistrationRequestStatus status);

    List<IndividualRegistrationRequest> findAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequest> findAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonId(Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonIdAndStatus(
            Long hackathonId, IndividualRegistrationRequestStatus status);
}
