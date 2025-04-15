package com.hacof.identity.repository;

import com.hacof.identity.entity.FileUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    Optional<FileUrl> findById(Long id);
}
