package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {}
