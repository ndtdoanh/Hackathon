package com.hacof.identity.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.hacof.identity.dto.request.UserCreateRequest;
import com.hacof.identity.dto.request.UserUpdateRequest;
import com.hacof.identity.dto.response.HackathonResponse;
import com.hacof.identity.dto.response.HackathonResultResponse;
import com.hacof.identity.dto.response.SimpleRoleResponse;
import com.hacof.identity.dto.response.TeamResponse;
import com.hacof.identity.dto.response.UserHackathonResponse;
import com.hacof.identity.dto.response.UserResponse;
import com.hacof.identity.dto.response.UserRoleResponse;
import com.hacof.identity.dto.response.UserTeamResponse;
import com.hacof.identity.entity.Team;
import com.hacof.identity.entity.User;
import com.hacof.identity.entity.UserHackathon;
import com.hacof.identity.entity.UserRole;
import com.hacof.identity.entity.UserTeam;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class})
public interface UserMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "userRoles", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))")
    @Mapping(
            target = "createdByUserName",
            expression = "java(user.getCreatedBy() != null ? user.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    @Mapping(target = "userRoles", source = "userRoles", qualifiedByName = "mapUserRoles")
    @Mapping(target = "userHackathons", source = "userHackathons", qualifiedByName = "mapUserHackathons")
    @Mapping(target = "userTeams", source = "userTeams", qualifiedByName = "mapUserTeams")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "bio", source = "bio")
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "avatarUrl", source = "avatarUrl")
    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Named("mapUserRoles")
    default Set<UserRoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        }
        return userRoles.stream()
                .map(userRole -> UserRoleResponse.builder()
                        .id(String.valueOf(userRole.getId()))
                        .role(new SimpleRoleResponse(
                                String.valueOf(userRole.getRole().getId()),
                                userRole.getRole().getName()))
                        .build())
                .collect(Collectors.toSet());
    }

    @Named("mapUserHackathons")
    default Set<UserHackathonResponse> mapUserHackathons(Set<UserHackathon> userHackathons) {
        if (userHackathons == null) {
            return null;
        }
        return userHackathons.stream()
                .map(userHackathon -> UserHackathonResponse.builder()
                        .id(String.valueOf(userHackathon.getId()))
                        .role(userHackathon.getRole())
                        .hackathon(HackathonResponse.builder()
                                .id(String.valueOf(userHackathon.getHackathon().getId()))
                                .bannerImageUrl(userHackathon.getHackathon().getBannerImageUrl())
                                .title(userHackathon.getHackathon().getTitle())
                                .startDate(userHackathon
                                        .getHackathon()
                                        .getStartDate()
                                        .toLocalDate())
                                .endDate(userHackathon
                                        .getHackathon()
                                        .getEndDate()
                                        .toLocalDate())
                                .status(userHackathon.getHackathon().getStatus())
                                .hackathonResults(userHackathon.getHackathon().getHackathonResults().stream()
                                        .map(result -> HackathonResultResponse.builder()
                                                .id(String.valueOf(result.getId()))
                                                .teamId(String.valueOf(
                                                        result.getTeam().getId()))
                                                .placement(result.getPlacement())
                                                .totalScore(result.getTotalScore())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .build())
                .collect(Collectors.toSet());
    }

    @Named("mapUserTeams")
    default Set<UserTeamResponse> mapUserTeams(Set<UserTeam> userTeams) {
        if (userTeams == null) {
            return null;
        }
        return userTeams.stream()
                .map(userTeam -> {
                    Team team = userTeam.getTeam();
                    String hackathonId = team.getTeamHackathons().stream()
                            .findFirst()
                            .map(teamHackathon ->
                                    String.valueOf(teamHackathon.getHackathon().getId()))
                            .orElse(null);

                    return UserTeamResponse.builder()
                            .id(String.valueOf(userTeam.getId()))
                            .team(TeamResponse.builder()
                                    .id(String.valueOf(team.getId()))
                                    .name(team.getName())
                                    .build())
                            .build();
                })
                .collect(Collectors.toSet());
    }
}
