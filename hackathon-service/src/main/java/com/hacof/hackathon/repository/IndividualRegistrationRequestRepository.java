package com.hacof.hackathon.repository;

import java.util.List;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.constant.IndividualRegistrationRequestStatus;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import org.springframework.data.jpa.repository.Query;

public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {
    List<IndividualRegistrationRequest> findAllByCreatedByUsername(String createdByUsername);

    List<IndividualRegistrationRequest> findAllByCreatedByUsernameAndHackathonId(
            String createdByUsername, Long hackathonId);

    List<IndividualRegistrationRequest> findAllByHackathonId(Long hackathonId);

//    List<IndividualRegistrationRequest> findAllByHackathonIdAndStatus(
//            Long hackathonId, IndividualRegistrationRequestStatus status);

    @Query("SELECT r FROM IndividualRegistrationRequest r WHERE r.hackathon.id = :hackathonId AND r.status = :status")
    List<IndividualRegistrationRequest> findAllByHackathonIdAndStatus(@Param("hackathonId") Long hackathonId, @Param("status") IndividualRegistrationRequestStatus status);

}
