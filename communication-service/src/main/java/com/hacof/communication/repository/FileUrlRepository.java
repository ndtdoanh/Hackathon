package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.FileUrl;

@Repository
public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {
    List<FileUrl> findAllByFileUrlInAndMessageIsNull(List<String> fileUrls);

    List<FileUrl> findAllByFileUrlInAndTaskIsNull(List<String> fileUrls);

    List<FileUrl> findAllByFileUrlInAndScheduleEventIsNull(List<String> fileUrls);
}
