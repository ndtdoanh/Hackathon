package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;

@Mapper(componentModel = "spring")
public interface SponsorshipMapper {
    SponsorshipDTO toDto(Sponsorship sponsorship);

    Sponsorship toEntity(SponsorshipDTO sponsorshipDTO);

    void updateEntityFromDto(SponsorshipDTO dto, @MappingTarget Sponsorship entity);
}
