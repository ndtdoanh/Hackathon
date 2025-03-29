package com.hacof.submission.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;

@Component
public class JudgeSubmissionMapper {

    public JudgeSubmission toEntity(JudgeSubmissionRequestDTO dto) {
        JudgeSubmission entity = new JudgeSubmission();

        User judge = new User();
        judge.setId(dto.getJudgeId());
        entity.setJudge(judge);

        Submission submission = new Submission();
        submission.setId(dto.getSubmissionId());
        entity.setSubmission(submission);

        entity.setNote(dto.getNote());
        entity.setScore(0);

        return entity;
    }

    public JudgeSubmissionResponseDTO toResponseDTO(JudgeSubmission entity) {
        return JudgeSubmissionResponseDTO.builder()
                .id(String.valueOf(entity.getId()))
                .judge(mapUserToDto(entity.getJudge()))
                .submission(mapSubmissionToDto(entity.getSubmission()))
                .score(entity.getScore())
                .note(entity.getNote())
                .createdDate(
                        entity.getCreatedDate() != null
                                ? entity.getCreatedDate().toString()
                                : null)
                .lastModifiedDate(
                        entity.getLastModifiedDate() != null
                                ? entity.getLastModifiedDate().toString()
                                : null)
                .judgeSubmissionDetails(
                        entity.getJudgeSubmissionDetails() != null
                                ? entity.getJudgeSubmissionDetails().stream()
                                        .map(this::mapJudgeSubmissionDetailToDto)
                                        .collect(Collectors.toSet())
                                : Collections.emptySet())
                .build();
    }

    public List<JudgeSubmissionResponseDTO> toResponseDTOList(List<JudgeSubmission> entities) {
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private JudgeSubmissionDetailResponseDTO mapJudgeSubmissionDetailToDto(JudgeSubmissionDetail detail) {
        return JudgeSubmissionDetailResponseDTO.builder()
                .id(String.valueOf(detail.getId()))
                .score(detail.getScore())
                .note(detail.getNote())
                .judgeSubmissionId(
                        detail.getJudgeSubmission() != null
                                ? detail.getJudgeSubmission().getId()
                                : null)
                .roundMarkCriterion(mapRoundMarkCriterionToDto(detail.getRoundMarkCriterion()))
                .createdDate(detail.getCreatedDate())
                .lastModifiedDate(detail.getLastModifiedDate())
                .build();
    }

    private RoundMarkCriterionResponseDTO mapRoundMarkCriterionToDto(RoundMarkCriterion criterion) {
        return RoundMarkCriterionResponseDTO.builder()
                .id(String.valueOf(criterion.getId()))
                .round(
                        criterion.getRound() != null
                                ? mapRoundToDto(criterion.getRound())
                                : null)
                .name(criterion.getName())
                .maxScore(criterion.getMaxScore())
                .note(criterion.getNote())
                .createdBy(
                        criterion.getCreatedBy() != null
                                ? criterion.getCreatedBy().getUsername()
                                : null)
                .createdDate(criterion.getCreatedDate())
                .lastModifiedDate(criterion.getLastModifiedDate())
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
                .createdDate(round.getCreatedDate())
                .lastModifiedDate(round.getLastModifiedDate())
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
                .build();
    }
}
