package com.hacof.analytics.repository;

import com.hacof.analytics.constant.BlogPostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.entity.BlogPost;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    boolean existsBySlug(String slug);

    List<BlogPost> findByStatus(BlogPostStatus status);
}
