package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.FileUrl;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {}
