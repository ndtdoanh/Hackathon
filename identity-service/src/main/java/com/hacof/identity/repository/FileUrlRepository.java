package com.hacof.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.FileUrl;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    Optional<FileUrl> findById(Long id);
}
