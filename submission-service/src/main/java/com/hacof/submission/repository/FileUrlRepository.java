package com.hacof.submission.repository;

import com.hacof.submission.entity.FileUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {}
