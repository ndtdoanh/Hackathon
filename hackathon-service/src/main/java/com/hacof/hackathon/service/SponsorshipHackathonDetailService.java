package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailRequestDTO;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailResponseDTO;

public interface SponsorshipHackathonDetailService {
    void delete(Long id);

    List<SponsorshipHackathonDetailResponseDTO> getAll();

    SponsorshipHackathonDetailResponseDTO getById(Long id);

    List<SponsorshipHackathonDetailResponseDTO> getAllBySponsorshipHackathonId(String sponsorshipHackathonId);

    List<FileUrlResponse> getFileUrlsBySponsorshipHackathonDetailId(Long sponsorshipHackathonDetailId);

    SponsorshipHackathonDetailResponseDTO createWithFiles(SponsorshipHackathonDetailRequestDTO dto);

    SponsorshipHackathonDetailResponseDTO updateInfo(Long id, SponsorshipHackathonDetailRequestDTO dto);

    SponsorshipHackathonDetailResponseDTO updateFiles(Long id, List<String> fileUrls);
}
