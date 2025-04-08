package com.hacof.hackathon.mapper;

// @Mapper(componentModel = "spring")
// public interface MentorTeamLimitMapper {
//    @Mapping(target = "id", expression = "java(String.valueOf(mentorTeamLimit.getId()))")
//    @Mapping(target = "hackathonId", expression = "java(String.valueOf(mentorTeamLimit.getHackathon().getId()))")
//    @Mapping(target = "mentorId", expression = "java(String.valueOf(mentorTeamLimit.getMentor().getId()))")
//    @Mapping(target = "teamId", expression = "java(String.valueOf(mentorTeamLimit.getTeam().getId()))")
//    @Mapping(
//            target = "createdByUserName",
//            expression =
//                    "java(mentorTeamLimit.getCreatedBy() != null ? mentorTeamLimit.getCreatedBy().getUsername() :
// null)")
//    @Mapping(
//            target = "lastModifiedByUserName",
//            expression =
//                    "java(mentorTeamLimit.getLastModifiedBy() != null ?
// mentorTeamLimit.getLastModifiedBy().getUsername() : null)")
//    @Mapping(target = "createdAt", source = "createdDate")
//    @Mapping(target = "updatedAt", source = "lastModifiedDate")
//    MentorTeamLimitDTO toDto(MentorTeamLimit mentorTeamLimit);
//
//    @Mapping(target = "hackathon.id", source = "hackathonId")
//    @Mapping(target = "mentor.id", source = "mentorId")
//    @Mapping(target = "team.id", source = "teamId")
//    MentorTeamLimit toEntity(MentorTeamLimitDTO mentorTeamLimitDTO);
//
//    void updateEntityFromDto(MentorTeamLimitDTO mentorTeamLimitDTO, @MappingTarget MentorTeamLimit mentorTeamLimit);
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
