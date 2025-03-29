package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

@Mapper(componentModel = "spring")
public interface SponsorshipHackathonDetailMapper {
    SponsorshipHackathonDetailDTO toDto(SponsorshipHackathonDetail sponsorshipHackathonDetail);

    SponsorshipHackathonDetail toEntity(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    void updateEntityFromDto(SponsorshipHackathonDetailDTO dto, @MappingTarget SponsorshipHackathonDetail entity);
}
