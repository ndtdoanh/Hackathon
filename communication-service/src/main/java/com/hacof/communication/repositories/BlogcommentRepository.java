package com.hacof.communication.repositories;

import com.hacof.communication.entities.Blogcomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogcommentRepository extends JpaRepository<Blogcomment, Long> {
    List<Blogcomment> findByPostId(Long postId);
}
