package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.RoundLocation;

@Repository
public interface RoundLocationRepository extends JpaRepository<RoundLocation, Long> {}
