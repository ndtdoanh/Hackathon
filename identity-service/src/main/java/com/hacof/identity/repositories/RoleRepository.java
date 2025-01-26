package com.hacof.identity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.identity.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
