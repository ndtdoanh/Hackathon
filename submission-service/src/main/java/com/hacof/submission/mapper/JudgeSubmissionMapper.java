package com.hacof.submission.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;

@Component
public class JudgeSubmissionMapper {

    // **Chuyển DTO thành Entity**
    public JudgeSubmission toEntity(
            JudgeSubmissionRequestDTO dto, User judge, Submission submission, Set<JudgeSubmissionDetail> details) {
        return JudgeSubmission.builder()
                .judge(judge)
                .submission(submission)
                .score(dto.getScore())
                .note(dto.getNote())
                .judgeSubmissionDetails(details)
                .build();
    }

    public JudgeSubmissionDetail toDetailEntity(
            JudgeSubmissionDetailRequestDTO dto, RoundMarkCriterion criterion, JudgeSubmission judgeSubmission) {
        return JudgeSubmissionDetail.builder()
                .judgeSubmission(judgeSubmission)
                .roundMarkCriterion(criterion)
                .score(dto.getScore())
                .note(dto.getNote())
                .build();
    }

    // **Chuyển Entity thành ResponseDTO**
    public JudgeSubmissionResponseDTO toResponseDTO(JudgeSubmission entity) {
        return JudgeSubmissionResponseDTO.builder()
                .id(String.valueOf(entity.getId()))
                .judge(mapUserToDto(entity.getJudge()))
                .submission(mapSubmissionToDto(entity.getSubmission()))
                .score(entity.getScore())
                .note(entity.getNote())
                .judgeSubmissionDetails(
                        entity.getJudgeSubmissionDetails() != null
                                        && !entity.getJudgeSubmissionDetails().isEmpty()
                                ? entity.getJudgeSubmissionDetails().stream()
                                        .map(this::mapJudgeSubmissionDetailToDto)
                                        .collect(Collectors.toList())
                                : Collections.emptyList())
                .createdAt(entity.getCreatedDate().toString())
                .updatedAt(entity.getLastModifiedDate().toString())
                .build();
    }

    public JudgeSubmissionDetailResponseDTO mapJudgeSubmissionDetailToDto(JudgeSubmissionDetail detail) {
        return JudgeSubmissionDetailResponseDTO.builder()
                .id(String.valueOf(detail.getId()))
                .roundMarkCriterion(mapRoundMarkCriterionToDto(detail.getRoundMarkCriterion())) // ✅ Gọi hàm map
                .score(detail.getScore())
                .note(detail.getNote())
                .createdAt(detail.getCreatedDate())
                .updatedAt(detail.getLastModifiedDate())
                .build();
    }

    private RoundMarkCriterionResponseDTO mapRoundMarkCriterionToDto(RoundMarkCriterion criterion) {
        return RoundMarkCriterionResponseDTO.builder()
                .id(String.valueOf(criterion.getId()))
                .name(criterion.getName())
                .round(mapRoundToDto(criterion.getRound()))
                .maxScore(criterion.getMaxScore())
                .note(criterion.getNote())
                .createdAt(criterion.getCreatedDate())
                .updatedAt(criterion.getLastModifiedDate())
                .createdByUserName(criterion.getCreatedBy().getUsername())
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

    private SubmissionResponseDTO mapSubmissionToDto(Submission submission) {
        return SubmissionResponseDTO.builder()
                .id(String.valueOf(submission.getId()))
                .round(mapRoundToDto(submission.getRound()))
                .team(mapTeamToDto(submission.getTeam()))
                .status(submission.getStatus() != null ? submission.getStatus().name() : "UNKNOWN")
                .submittedAt(submission.getSubmittedAt())
                .finalScore(submission.getFinalScore())
                .fileUrls(
                        submission.getFileUrls() != null
                                ? submission.getFileUrls().stream()
                                        .map(file -> new FileUrlResponseDTO(
                                                file.getFileName(),
                                                file.getFileUrl(),
                                                file.getFileType(),
                                                file.getFileSize()))
                                        .collect(Collectors.toList())
                                : Collections.emptyList())
                .createdAt(submission.getCreatedDate())
                .updatedAt(submission.getLastModifiedDate())
                .createdByUserName(submission.getCreatedBy().getUsername())
                .build();
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

    private TeamResponseDTO mapTeamToDto(Team team) {
        if (team == null) {
            return null;
        }

        return TeamResponseDTO.builder()
                .id(String.valueOf(team.getId()))
                .name(team.getName())
                .teamLeader(team.getTeamLeader() != null ? mapUserToDto(team.getTeamLeader()) : null)
                .teamMembers(
                        team.getTeamMembers() != null
                                ? team.getTeamMembers().stream()
                                        .map(userTeam -> mapUserToDto(userTeam.getUser()))
                                        .collect(Collectors.toList())
                                : null)
                .bio(team.getBio())
                .isDeleted(team.getIsDeleted())
                .build();
    }
}
