package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;

@Mapper(componentModel = "spring")
public interface SponsorshipMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(sponsorship.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(sponsorship.getCreatedBy() != null ? sponsorship.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(sponsorship.getLastModifiedBy() != null ? sponsorship.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    SponsorshipDTO toDto(Sponsorship sponsorship);

    Sponsorship toEntity(SponsorshipDTO sponsorshipDTO);

    void updateEntityFromDto(SponsorshipDTO dto, @MappingTarget Sponsorship entity);
}
