package com.hacof.communication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.communication.entities.Blogcomment;

public interface BlogcommentRepository extends JpaRepository<Blogcomment, Long> {
    List<Blogcomment> findByPostId(Long postId);
}
