package com.hacof.hackathon.mapper;

//
// @Mapper(componentModel = "spring")
// public interface SponsorshipHackathonDetailMapper {
//    @Mapping(target = "id", expression = "java(String.valueOf(sponsorshipHackathonDetail.getId()))")
//    @Mapping(
//            target = "sponsorshipHackathonId",
//            expression =
//                    "java(sponsorshipHackathonDetail.getSponsorshipHackathon() != null ?
// String.valueOf(sponsorshipHackathonDetail.getSponsorshipHackathon().getId()) : null)")
//    @Mapping(
//            target = "createdByUserName",
//            expression =
//                    "java(sponsorshipHackathonDetail.getCreatedBy() != null ?
// sponsorshipHackathonDetail.getCreatedBy().getUsername() : null)")
//    @Mapping(
//            target = "lastModifiedByUserName",
//            expression =
//                    "java(sponsorshipHackathonDetail.getLastModifiedBy() != null ?
// sponsorshipHackathonDetail.getLastModifiedBy().getUsername() : null)")
//    @Mapping(target = "createdAt", source = "createdDate")
//    @Mapping(target = "updatedAt", source = "lastModifiedDate")
//    SponsorshipHackathonDetailDTO toDto(SponsorshipHackathonDetail sponsorshipHackathonDetail);
//
//    SponsorshipHackathonDetail toEntity(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);
//
//    void updateEntityFromDto(SponsorshipHackathonDetailDTO dto, @MappingTarget SponsorshipHackathonDetail entity);
// }
