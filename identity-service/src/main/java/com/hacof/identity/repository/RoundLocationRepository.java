package com.hacof.identity.repository;

import com.hacof.identity.entity.RoundLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundLocationRepository extends JpaRepository<RoundLocation, Long> {}
