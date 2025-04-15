package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HackathonRepository extends JpaRepository<Hackathon, Long>, JpaSpecificationExecutor<Hackathon> {
    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}
