package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;

@Mapper(componentModel = "spring")
public interface SponsorshipMapper {
    SponsorshipDTO toDTO(Sponsorship sponsorship);

    Sponsorship toEntity(SponsorshipDTO sponsorshipDTO);
}
