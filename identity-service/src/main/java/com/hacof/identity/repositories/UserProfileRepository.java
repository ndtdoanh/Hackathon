package com.hacof.identity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.identity.entities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<UserProfile> findAll();
}
