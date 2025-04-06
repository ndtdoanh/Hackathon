package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.TeamRequestMember;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(teamRequest.getId()))")
    @Mapping(target = "hackathonId", expression = "java(String.valueOf(teamRequest.getHackathon().getId()))")
    @Mapping(target = "reviewedBy", source = "reviewedBy")  // Map reviewedBy as User
    @Mapping(target = "createdByUserName", expression = "java(teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null)")
    @Mapping(target = "lastModifiedByUserName", expression = "java(teamRequest.getLastModifiedBy() != null ? teamRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "teamRequestMembers", source = "teamRequestMembers")  // Map teamRequestMembers to DTO
    TeamRequestDTO toDto(TeamRequest teamRequest);

    @Mapping(target = "hackathon.id", source = "hackathonId", qualifiedByName = "stringToLong")  // Map hackathonId correctly
    @Mapping(target = "reviewedBy", source = "reviewedBy")  // Map User to User
    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    // Mapping reviewedBy as User to User
    @Named("userToUser")
    default User userToUser(User user) {
        if (user == null) return null;
        return user.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())  // Map phoneNumber
                .avatarUrl(user.getAvatarUrl())            // Map avatar
                .bio(user.getBio())                  // Map bio
                .build();    }

    @Named("userToUserDTO")
    default User userToUserDTO(User user) {
        if (user == null) return null;
        return user.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())  // Map phoneNumber
                .avatarUrl(user.getAvatarUrl())            // Map avatar
                .bio(user.getBio())                  // Map bio
                .build();

    }

    @Named("teamRequestMembersToDTO")
    default List<TeamRequestMemberDTO> teamRequestMembersToDTO(List<TeamRequestMember> members) {
        if (members == null || members.isEmpty()) {
            return null;
        }
        return members.stream()
                .map(member -> TeamRequestMemberDTO.builder()
                        .id(String.valueOf(member.getId()))
                        .teamRequestId(String.valueOf(member.getTeamRequest().getId()))
                        .user(userToUserDTO(member.getUser()))  // Mapping full user data
                        .status(member.getStatus() != null ? member.getStatus().name() : null)  // Assuming you want the status as a String
                        .respondedAt(member.getRespondedAt() != null ? member.getRespondedAt().toString() : null)
                        .build())
                .collect(Collectors.toList());
    }

    // Converting String (ID) to Long (Hackathon ID)
    @Named("stringToLong")
    default Long stringToLong(String id) {
        return id != null ? Long.parseLong(id) : null;
    }
}
