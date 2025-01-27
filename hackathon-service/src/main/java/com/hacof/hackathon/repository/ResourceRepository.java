package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {}
