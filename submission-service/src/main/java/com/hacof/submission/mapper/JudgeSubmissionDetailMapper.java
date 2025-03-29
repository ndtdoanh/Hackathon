package com.hacof.submission.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.*;
import com.hacof.submission.entity.*;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.RoundMarkCriterionRepository;

@Component
public class JudgeSubmissionDetailMapper {

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private RoundMarkCriterionRepository roundMarkCriterionRepository;

    public JudgeSubmissionDetail toEntity(JudgeSubmissionDetailRequestDTO dto) {
        JudgeSubmissionDetail entity = new JudgeSubmissionDetail();
        entity.setScore(dto.getScore());
        entity.setNote(dto.getNote());

        entity.setJudgeSubmission(judgeSubmissionRepository
                .findById(dto.getJudgeSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Judge Submission not found with ID " + dto.getJudgeSubmissionId())));

        entity.setRoundMarkCriterion(roundMarkCriterionRepository
                .findById(dto.getRoundMarkCriterionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Round Mark Criterion not found with ID " + dto.getRoundMarkCriterionId())));

        return entity;
    }

    public JudgeSubmissionDetailResponseDTO toResponseDTO(JudgeSubmissionDetail entity) {
        return JudgeSubmissionDetailResponseDTO.builder()
                .id(String.valueOf(entity.getId()))
                .score(entity.getScore())
                .note(entity.getNote())
                .judgeSubmissionId(entity.getJudgeSubmission().getId())
                .roundMarkCriterion(mapRoundMarkCriterionToDto(entity.getRoundMarkCriterion()))
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    public List<JudgeSubmissionDetailResponseDTO> toResponseDTOList(List<JudgeSubmissionDetail> entities) {
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    //    // ✅ Convert JudgeSubmission to DTO
    //    private JudgeSubmissionResponseDTO mapJudgeSubmissionToDto(JudgeSubmission judgeSubmission) {
    //        if (judgeSubmission == null) {
    //            return null;
    //        }
    //
    //        return JudgeSubmissionResponseDTO.builder()
    //                .id(judgeSubmission.getId())
    //                .judge(mapUserToDto(judgeSubmission.getJudge()))
    //                .submission(mapSubmissionToDto(judgeSubmission.getSubmission()))
    //                .score(judgeSubmission.getScore())
    //                .note(judgeSubmission.getNote())
    //                .createdDate(judgeSubmission.getCreatedDate() != null ?
    // judgeSubmission.getCreatedDate().toString() : null)
    //                .lastModifiedDate(judgeSubmission.getLastModifiedDate() != null ?
    // judgeSubmission.getLastModifiedDate().toString() : null)
    //                .build();
    //    }

    private RoundMarkCriterionResponseDTO mapRoundMarkCriterionToDto(RoundMarkCriterion criterion) {
        if (criterion == null) {
            return null;
        }

        return RoundMarkCriterionResponseDTO.builder()
                .id(String.valueOf(criterion.getId()))
                .round(criterion.getRound() != null ? mapRoundToDto(criterion.getRound()) : null)
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
        if (round == null) {
            return null;
        }

        return RoundResponseDTO.builder()
                .id(String.valueOf(round.getId()))
                .roundTitle(round.getRoundTitle())
                .startTime(round.getStartTime())
                .endTime(round.getEndTime())
                .status(round.getStatus() != null ? round.getStatus().name() : "UNKNOWN")
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
}
