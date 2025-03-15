package com.hacof.communication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entities.Blogpost;

@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {}
