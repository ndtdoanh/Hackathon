package com.hacof.identity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.identity.entities.Userprofile;

public interface UserProfileRepository extends JpaRepository<Userprofile, Long> {
    Optional<Userprofile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    List<Userprofile> findAll();
}
