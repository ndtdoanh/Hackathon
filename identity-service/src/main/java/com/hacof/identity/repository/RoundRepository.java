package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {}
