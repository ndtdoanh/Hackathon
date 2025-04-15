package com.hacof.communication.repository;

import com.hacof.communication.entity.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Long> {
    boolean existsByName(String name);
}
