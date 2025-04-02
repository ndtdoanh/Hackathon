package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;
import com.hacof.hackathon.entity.SponsorshipHackathon;

@Mapper(componentModel = "spring")
public interface SponsorshipHackathonMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(sponsorshipHackathon.getId()))")
    @Mapping(
            target = "hackathonId",
            expression =
                    "java(sponsorshipHackathon.getHackathon() != null ? String.valueOf(sponsorshipHackathon.getHackathon().getId()) : null)")
    @Mapping(
            target = "sponsorshipId",
            expression =
                    "java(sponsorshipHackathon.getSponsorship() != null ? String.valueOf(sponsorshipHackathon.getSponsorship().getId()) : null)")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(sponsorshipHackathon.getCreatedBy() != null ? sponsorshipHackathon.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(sponsorshipHackathon.getLastModifiedBy() != null ? sponsorshipHackathon.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    SponsorshipHackathonDTO toDto(SponsorshipHackathon sponsorshipHackathon);

    SponsorshipHackathon toEntity(SponsorshipHackathonDTO sponsorshipHackathonDTO);

    void updateEntityFromDto(SponsorshipHackathonDTO dto, @MappingTarget SponsorshipHackathon entity);
}
