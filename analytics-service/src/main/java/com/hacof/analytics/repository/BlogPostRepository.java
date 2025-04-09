package com.hacof.analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.constant.BlogPostStatus;
import com.hacof.analytics.entity.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    boolean existsBySlug(String slug);

    List<BlogPost> findByStatus(BlogPostStatus status);
}
