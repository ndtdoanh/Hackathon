package com.hacof.submission.mapper;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.HackathonResponseDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.dto.response.PermissionResponse;
import com.hacof.submission.dto.response.RoleResponse;
import com.hacof.submission.dto.response.RoundResponseDTO;
import com.hacof.submission.dto.response.UserResponse;
import com.hacof.submission.entity.Hackathon;
import com.hacof.submission.entity.JudgeRound;
import com.hacof.submission.entity.Permission;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.User;
import com.hacof.submission.entity.UserRole;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JudgeRoundMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    public JudgeRound toEntity(JudgeRoundRequestDTO dto) {
        JudgeRound judgeRound = new JudgeRound();

        User judge = userRepository
                .findById(dto.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));
        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found"));
        judgeRound.setJudge(judge);
        judgeRound.setRound(round);

        return judgeRound;
    }

    public JudgeRoundResponseDTO toResponseDTO(JudgeRound entity) {
        if (entity == null) {
            return null;
        }

        return JudgeRoundResponseDTO.builder()
                .id(String.valueOf(entity.getId()))
                .judge(mapUserToDto(entity.getJudge()))
                .round(mapRoundToDto(entity.getRound()))
                .isDeleted(entity.isDeleted())
                .createdByUserName(
                        entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null)
                .createdAt(
                        entity.getCreatedDate() != null
                                ? entity.getCreatedDate().toString()
                                : null)
                .updatedAt(
                        entity.getLastModifiedDate() != null
                                ? entity.getLastModifiedDate().toString()
                                : null)
                .build();
    }

    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isVerified(user.getIsVerified())
                .status(user.getStatus())
                .build();
    }

    private Set<RoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> RoleResponse.builder()
                        .id(String.valueOf(userRole.getRole().getId()))
                        .name(userRole.getRole().getName())
                        .description(userRole.getRole().getDescription())
                        .createdAt(userRole.getRole().getCreatedDate())
                        .updatedAt(userRole.getRole().getLastModifiedDate())
                        .createdByUserName(
                                userRole.getRole().getCreatedBy() != null
                                        ? userRole.getRole().getCreatedBy().getId()
                                        : null)
                        .permissions(
                                userRole.getRole().getPermissions() != null
                                        ? mapPermissions(userRole.getRole().getPermissions())
                                        : Collections.emptySet())
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> PermissionResponse.builder()
                        .id(String.valueOf(permission.getId()))
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .build())
                .collect(Collectors.toSet());
    }

    private RoundResponseDTO mapRoundToDto(Round round) {
        return RoundResponseDTO.builder()
                .id(String.valueOf(round.getId()))
                .hackathon(round.getHackathon() != null ? mapHackathonToDto(round.getHackathon()) : null)
                .startTime(round.getStartTime())
                .endTime(round.getEndTime())
                .roundNumber(round.getRoundNumber())
                .roundTitle(round.getRoundTitle())
                .status(round.getStatus() != null ? round.getStatus().name() : "UNKNOWN")
                .createdAt(round.getCreatedDate())
                .updatedAt(round.getLastModifiedDate())
                .build();
    }

    private HackathonResponseDTO mapHackathonToDto(Hackathon hackathon) {
        if (hackathon == null) {
            return null;
        }

        return HackathonResponseDTO.builder()
                .id(String.valueOf(hackathon.getId()))
                .title(hackathon.getTitle())
                .subTitle(hackathon.getSubTitle())
                .bannerImageUrl(hackathon.getBannerImageUrl())
                .description(hackathon.getDescription())
                .information(hackathon.getInformation())
                .startDate(hackathon.getStartDate())
                .endDate(hackathon.getEndDate())
                .maxTeams(hackathon.getMaxTeams())
                .minTeamSize(hackathon.getMinTeamSize())
                .maxTeamSize(hackathon.getMaxTeamSize())
                .contact(hackathon.getContact())
                .category(String.valueOf(hackathon.getCategory()))
                .status(hackathon.getStatus() != null ? hackathon.getStatus().name() : "UNKNOWN")
                .createAt(hackathon.getCreatedDate())
                .updateAt(hackathon.getLastModifiedDate())
                .createdByUserName(
                        hackathon.getCreatedBy() != null
                                ? hackathon.getCreatedBy().getUsername()
                                : null)
                .build();
    }
}
