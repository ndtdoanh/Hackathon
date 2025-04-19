package com.hacof.hackathon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.Hackathon;

public interface FileUrlRepository extends JpaRepository<FileUrl, Long> {

    List<FileUrl> findAllByFileUrlInAndHackathonIsNull(List<String> fileUrls);

    Optional<FileUrl> findByFileUrlAndHackathonIsNull(String fileUrl);

    List<FileUrl> findAllByHackathon(Hackathon hackathon);

    List<FileUrl> findAllByFileUrlInAndSponsorshipHackathonDetailIsNull(List<String> fileUrls);
}
