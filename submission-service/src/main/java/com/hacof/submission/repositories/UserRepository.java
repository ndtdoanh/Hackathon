package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
