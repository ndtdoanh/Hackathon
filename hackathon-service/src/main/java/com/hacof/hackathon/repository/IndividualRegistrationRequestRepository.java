package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.IndividualRegistrationRequest;

@Repository
public interface IndividualRegistrationRequestRepository extends JpaRepository<IndividualRegistrationRequest, Long> {}
