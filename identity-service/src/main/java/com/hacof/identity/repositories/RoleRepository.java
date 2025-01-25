package com.hacof.identity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.identity.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
