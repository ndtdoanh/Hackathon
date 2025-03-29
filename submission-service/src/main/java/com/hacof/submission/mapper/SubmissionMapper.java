package com.hacof.submission.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.submission.constant.Status;
import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;
import com.hacof.submission.repository.RoundRepository;

@Component
public class SubmissionMapper {

    public static Submission toEntity(SubmissionRequestDTO dto, RoundRepository roundRepository) {
        Submission submission = new Submission();

        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found with ID " + dto.getRoundId()));

        submission.setRound(round);
        submission.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        return submission;
    }

    public SubmissionResponseDTO toResponseDTO(Submission submission) {
        if (submission == null) {
            return null;
        }

        return SubmissionResponseDTO.builder()
                .id(String.valueOf(submission.getId()))
                .round(mapRoundToDto(submission.getRound()))
                .status(submission.getStatus() != null ? submission.getStatus().name() : "UNKNOWN")
                .submittedAt(submission.getSubmittedAt())
                .fileUrls(mapFileUrls(submission))
                .judgeSubmissions(mapJudgeSubmissions(submission))
                .finalScore(submission.getFinalScore())
                .build();
    }

    public List<SubmissionResponseDTO> toResponseDTOList(List<Submission> submissions) {
        if (submissions == null || submissions.isEmpty()) {
            return List.of();
        }
        return submissions.stream().map(this::toResponseDTO).collect(Collectors.toList());
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

    private List<FileUrlResponseDTO> mapFileUrls(Submission submission) {
        if (submission.getFileUrls() == null || submission.getFileUrls().isEmpty()) {
            return Collections.emptyList();
        }

        return submission.getFileUrls().stream()
                .map(file -> new FileUrlResponseDTO(
                        file.getFileName(), file.getFileUrl(), file.getFileType(), file.getFileSize()))
                .collect(Collectors.toList());
    }

    private List<JudgeSubmissionResponseDTO> mapJudgeSubmissions(Submission submission) {
        if (submission.getJudgeSubmissions() == null
                || submission.getJudgeSubmissions().isEmpty()) {
            return Collections.emptyList();
        }

        return submission.getJudgeSubmissions().stream()
                .map(judgeSubmission -> JudgeSubmissionResponseDTO.builder()
                        .id(String.valueOf(judgeSubmission.getId()))
                        .judge(mapUserToDto(judgeSubmission.getJudge()))
                        .score(judgeSubmission.getScore())
                        .note(judgeSubmission.getNote())
                        .createdDate(
                                judgeSubmission.getCreatedDate() != null
                                        ? judgeSubmission.getCreatedDate().toString()
                                        : null)
                        .lastModifiedDate(
                                judgeSubmission.getLastModifiedDate() != null
                                        ? judgeSubmission.getLastModifiedDate().toString()
                                        : null)
                        .build())
                .collect(Collectors.toList());
    }

    private UserResponse mapUserToDto(User user) {
        if (user == null) {
            return null;
        }

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
        if (userRoles == null || userRoles.isEmpty()) {
            return Collections.emptySet();
        }

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
                        .permissions(mapPermissions(userRole.getRole().getPermissions()))
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return Collections.emptySet();
        }

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
}
