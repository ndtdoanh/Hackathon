package com.hacof.communication.repository;

import com.hacof.communication.entity.Role;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT ur.user FROM UserRole ur WHERE ur.role = :role")
    List<User> findUsersByRole(@Param("role") Role role);

    @Query("SELECT ur FROM UserRole ur WHERE ur.user IN :users")
    List<UserRole> findUserRolesByUsers(@Param("users") List<User> users);
}
