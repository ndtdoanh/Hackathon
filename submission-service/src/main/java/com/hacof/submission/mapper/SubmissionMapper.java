package com.hacof.submission.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.hacof.submission.constant.Status;
import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.TeamRepository;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    public static Submission toEntity(SubmissionRequestDTO dto, RoundRepository roundRepository, TeamRepository teamRepository) {
        Submission submission = new Submission();

        Round round = roundRepository.findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found with ID " + dto.getRoundId()));
        submission.setRound(round);

        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID " + dto.getTeamId()));
        submission.setTeam(team);

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
                .team(mapTeamToDto(submission.getTeam()))
                .status(submission.getStatus() != null ? submission.getStatus().name() : "UNKNOWN")
                .submittedAt(submission.getSubmittedAt())
                .fileUrls(mapFileUrls(submission))
                .judgeSubmissions(mapJudgeSubmissions(submission))
                .finalScore(submission.getFinalScore())
                .createdAt(submission.getCreatedDate())
                .updatedAt(submission.getLastModifiedDate())
                .createdByUserName(submission.getCreatedBy() != null ? submission.getCreatedBy().getUsername() : null)
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
                .createdAt(round.getCreatedDate())
                .updatedAt(round.getLastModifiedDate())
                .build();
    }

    private TeamResponseDTO mapTeamToDto(Team team) {
        if (team == null) {
            return null;
        }

        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .teamLeader(team.getTeamLeader() != null ? mapUserToDto(team.getTeamLeader()) : null)
                .teamMembers(team.getTeamMembers() != null
                        ? team.getTeamMembers().stream()
                        .map(userTeam -> mapUserToDto(userTeam.getUser()))
                        .collect(Collectors.toList())
                        : null)
                .bio(team.getBio())
                .isDeleted(team.isDeleted())
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
                .createAt(hackathon.getCreatedDate())
                .updateAt(hackathon.getLastModifiedDate())
                .createdByUserName(hackathon.getCreatedBy() != null ? hackathon.getCreatedBy().getUsername() : null)
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
        if (submission.getJudgeSubmissions() == null || submission.getJudgeSubmissions().isEmpty()) {
            return Collections.emptyList();
        }

        return submission.getJudgeSubmissions().stream()
                .map(judgeSubmission -> JudgeSubmissionResponseDTO.builder()
                        .id(String.valueOf(judgeSubmission.getId()))
                        .judge(mapUserToDto(judgeSubmission.getJudge()))
                        .score(judgeSubmission.getScore())
                        .note(judgeSubmission.getNote())
                        .judgeSubmissionDetails(
                                judgeSubmission.getJudgeSubmissionDetails() != null && !judgeSubmission.getJudgeSubmissionDetails().isEmpty()
                                        ? judgeSubmission.getJudgeSubmissionDetails().stream()
                                        .map(this::mapJudgeSubmissionDetailToDto)
                                        .collect(Collectors.toList())
                                        : Collections.emptyList()
                        )
                        .createdAt(judgeSubmission.getCreatedDate() != null ? judgeSubmission.getCreatedDate().toString() : null)
                        .updatedAt(judgeSubmission.getLastModifiedDate() != null ? judgeSubmission.getLastModifiedDate().toString() : null)
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
                        .createdAt(userRole.getRole().getCreatedDate())
                        .updatedAt(userRole.getRole().getLastModifiedDate())
                        .createdByUserName(userRole.getRole().getCreatedBy() != null ? userRole.getRole().getCreatedBy().getId() : null)
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

}
