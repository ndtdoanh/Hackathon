package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Campus;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Campus findByName(String name);
}
