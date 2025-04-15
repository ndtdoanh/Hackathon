package com.hacof.identity.repository;

import com.hacof.identity.entity.UserHackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHackathonRepository extends JpaRepository<UserHackathon, Long> {
    List<UserHackathon> findByHackathon_Id(Long hackathonId);

    boolean existsByUserIdAndHackathonId(Long userId, Long hackathonId);

    List<UserHackathon> findByHackathonIdAndRoleIn(Long hackathonId, List<String> roles);
}
