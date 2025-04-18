package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.FileUrl;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {}
