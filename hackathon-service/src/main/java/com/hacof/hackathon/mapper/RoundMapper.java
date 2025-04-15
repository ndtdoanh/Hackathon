package com.hacof.hackathon.mapper;

// @Mapper(
//        componentModel = "spring",
//        uses = {RoundLocationMapper.class})
// public interface RoundMapper {
//
//    @Mapping(target = "id", expression = "java(String.valueOf(round.getId()))")
//    @Mapping(
//            target = "hackathonId",
//            expression = "java(round.getHackathon() != null ? String.valueOf(round.getHackathon().getId()) : null)")
//    @Mapping(target = "roundLocations", source = "roundLocations")
//    @Mapping(
//            target = "createdByUserName",
//            expression = "java(round.getCreatedBy() != null ? round.getCreatedBy().getUsername() : null)")
//    @Mapping(
//            target = "lastModifiedByUserName",
//            expression = "java(round.getLastModifiedBy() != null ? round.getLastModifiedBy().getUsername() : null)")
//    @Mapping(target = "createdAt", source = "createdDate")
//    @Mapping(target = "updatedAt", source = "lastModifiedDate")
//    RoundDTO toDto(Round round);
//
//    @Mapping(target = "hackathon", source = "hackathonId", qualifiedByName = "mapHackathonIdToEntity")
//    @Mapping(target = "roundLocations", ignore = true)
//    Round toEntity(
//            RoundDTO dto, @Context LocationService locationService, @Context RoundLocationMapper roundLocationMapper);
//
//    @Named("mapHackathonIdToEntity")
//    default Hackathon mapHackathonIdToEntity(String hackathonId) {
//        if (hackathonId == null) return null;
//        Hackathon hackathon = new Hackathon();
//        hackathon.setId(Long.parseLong(hackathonId));
//        return hackathon;
//    }
//
//    @AfterMapping
//    default void mapRoundLocations(
//            RoundDTO dto,
//            @MappingTarget Round round,
//            @Context LocationService locationService,
//            @Context RoundLocationMapper roundLocationMapper) {
//        if (dto.getRoundLocations() != null) {
//            List<RoundLocation> locations = dto.getRoundLocations().stream()
//                    .map(locDto -> roundLocationMapper.toEntity(locDto, locationService))
//                    .peek(loc -> loc.setRound(round)) // set round cho từng RoundLocation
//                    .collect(Collectors.toList());
//            round.setRoundLocations(locations);
//        }
//    }
//
//    default List<FileUrl> mapStringListToFileUrlList(List<String> values) {
//        if (values == null) {
//            return null;
//        }
//        return values.stream()
//                .map(value -> {
//                    FileUrl fileUrl = new FileUrl();
//                    fileUrl.setFileUrl(value); // Assuming FileUrl has a setFileUrl() method
//                    return fileUrl;
//                })
//                .collect(Collectors.toList());
//    }
// }
