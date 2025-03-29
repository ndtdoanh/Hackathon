package com.hacof.submission.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.UserRepository;

@Component
public class JudgeRoundMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    // Chuyển từ DTO sang Entity
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
                .id(entity.getId())
                .judge(mapUserToDto(entity.getJudge())) // ✅ Chuyển đổi toàn bộ thông tin Judge
                .round(mapRoundToDto(entity.getRound())) // ✅ Chuyển đổi toàn bộ thông tin Round
                .isDeleted(entity.isDeleted())
                .createdBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null)
                .createdDate(
                        entity.getCreatedDate() != null
                                ? entity.getCreatedDate().toString()
                                : null)
                .lastModifiedDate(
                        entity.getLastModifiedDate() != null
                                ? entity.getLastModifiedDate().toString()
                                : null)
                .build();
    }

    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isVerified(user.getIsVerified())
                .status(user.getStatus())
                .noPassword(user.getPassword() == null || user.getPassword().isEmpty()) // Derived field
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .createdByUserId(
                        user.getCreatedBy() != null ? user.getCreatedBy().getId() : null)
                .roles(user.getUserRoles() != null ? mapUserRoles(user.getUserRoles()) : Collections.emptySet())
                .build();
    }

    private Set<RoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> RoleResponse.builder()
                        .id(userRole.getRole().getId())
                        .name(userRole.getRole().getName())
                        .description(userRole.getRole().getDescription())
                        .createdDate(userRole.getRole().getCreatedDate())
                        .lastModifiedDate(userRole.getRole().getLastModifiedDate())
                        .createdByUserId(
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
                        .id(permission.getId())
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .build())
                .collect(Collectors.toSet());
    }

    private RoundResponseDTO mapRoundToDto(Round round) {
        return RoundResponseDTO.builder()
                .id(round.getId())
                .hackathon(round.getHackathon() != null ? mapHackathonToDto(round.getHackathon()) : null)
                .startTime(round.getStartTime())
                .endTime(round.getEndTime())
                .roundNumber(round.getRoundNumber())
                .roundTitle(round.getRoundTitle())
                .status(round.getStatus() != null ? round.getStatus().name() : "UNKNOWN")
                .createdDate(round.getCreatedDate())
                .lastModifiedDate(round.getLastModifiedDate())
                .build();
    }

    // Convert Hackathon entity to response DTO
    private HackathonResponseDTO mapHackathonToDto(Hackathon hackathon) {
        if (hackathon == null) {
            return null;
        }

        return HackathonResponseDTO.builder()
                .id(hackathon.getId())
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
                .category(hackathon.getCategory())
                .status(hackathon.getStatus() != null ? hackathon.getStatus().name() : "UNKNOWN")
                .createdDate(hackathon.getCreatedDate())
                .lastModifiedDate(hackathon.getLastModifiedDate())
                .createdBy(
                        hackathon.getCreatedBy() != null
                                ? hackathon.getCreatedBy().getUsername()
                                : null)
                .build();
    }
}
