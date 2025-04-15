package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {}
