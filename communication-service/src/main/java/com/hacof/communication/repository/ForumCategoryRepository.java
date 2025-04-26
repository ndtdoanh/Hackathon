package com.hacof.communication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ForumCategory;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Long> {
    boolean existsByName(String name);

    Optional<ForumCategory> findByName(String name);
}
