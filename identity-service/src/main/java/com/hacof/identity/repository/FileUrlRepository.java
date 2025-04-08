package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.FileUrl;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    Optional<FileUrl> findById(Long id);
}
