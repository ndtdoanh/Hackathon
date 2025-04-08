package com.hacof.hackathon.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.*;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(hackathon.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(hackathon.getCreatedBy() != null ? hackathon.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(hackathon.getLastModifiedBy() != null ? hackathon.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "minimumTeamMembers", source = "minTeamSize")
    @Mapping(target = "maximumTeamMembers", source = "maxTeamSize")
    @Mapping(target = "enrollmentCount", source = "maxTeams")
    @Mapping(target = "enrollmentStatus", expression = "java(determineEnrollmentStatus(hackathon))")
    @Mapping(target = "documentation", source = "documentation", qualifiedByName = "fileUrlListToStringList")
    HackathonDTO toDto(Hackathon hackathon);

    @Mapping(target = "minTeamSize", source = "minimumTeamMembers")
    @Mapping(target = "maxTeamSize", source = "maximumTeamMembers")
    @Mapping(target = "maxTeams", source = "enrollmentCount")
    Hackathon toEntity(HackathonDTO hackathonDTO);

    @Named("determineEnrollmentStatus")
    default String determineEnrollmentStatus(Hackathon hackathon) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(hackathon.getStartDate())) {
            return "UPCOMING";
        } else if (now.isAfter(hackathon.getStartDate()) && now.isBefore(hackathon.getEndDate())) {
            return "OPEN";
        } else {
            return "CLOSED";
        }
    }

    default User mapStringToUser(String username) {
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }

    @Named("fileUrlListToStringList")
    default List<String> map(List<FileUrl> value) {
        return value.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }

    default List<FileUrl> mapToFileUrlList(List<String> value) {
        return value.stream().map(FileUrl::new).collect(Collectors.toList());
    }
}
