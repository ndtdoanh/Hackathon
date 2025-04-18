package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.FileUrlResponse;
import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;

public interface SponsorshipHackathonDetailService {
    //    SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);
    //
    //    SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    void delete(Long id);

    List<SponsorshipHackathonDetailDTO> getAll();

    SponsorshipHackathonDetailDTO getById(Long id);

    List<SponsorshipHackathonDetailDTO> getAllBySponsorshipHackathonId(String sponsorshipHackathonId);

    List<FileUrlResponse> getFileUrlsBySponsorshipHackathonDetailId(Long sponsorshipHackathonDetailId);

    SponsorshipHackathonDetailDTO createWithFiles(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    SponsorshipHackathonDetailDTO updateInfo(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    SponsorshipHackathonDetailDTO updateFiles(Long id, List<String> fileUrls);
}
