package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.entity.SponsorshipHackathon;

@Mapper(componentModel = "spring")
public interface SponsorshipHackathonMapper {
    SponsorshipHackathonDTO toDto(SponsorshipHackathon sponsorshipHackathon);

    SponsorshipHackathon toEntity(SponsorshipHackathonDTO sponsorshipHackathonDTO);

    void updateEntityFromDto(SponsorshipHackathonDTO dto, @MappingTarget SponsorshipHackathon entity);
}
