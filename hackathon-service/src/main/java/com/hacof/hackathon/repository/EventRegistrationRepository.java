package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.EventRegistration;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {}
