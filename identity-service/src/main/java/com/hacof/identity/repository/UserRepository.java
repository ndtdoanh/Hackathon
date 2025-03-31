package com.hacof.identity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u JOIN u.userRoles ur JOIN ur.role r WHERE r.name IN (:roles)")
    List<User> findAllByRoles(@Param("roles") List<String> roles);

    @Query("SELECT u FROM User u JOIN u.userRoles ur WHERE ur.role.name = :role")
    List<User> findTeamMembers(@Param("role") String role);
}
