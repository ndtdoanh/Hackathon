package com.hacof.submission.mapper;

import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.HackathonResponseDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.dto.response.RoundResponseDTO;
import com.hacof.submission.entity.Hackathon;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.RoundMarkCriterion;

@Component
public class RoundMarkCriterionMapper {

    public RoundMarkCriterion toEntity(RoundMarkCriterionRequestDTO requestDTO) {
        return RoundMarkCriterion.builder()
                .name(requestDTO.getName())
                .maxScore(requestDTO.getMaxScore())
                .note(requestDTO.getNote())
                .build();
    }

    public RoundMarkCriterionResponseDTO toRoundMarkCriterionResponseDTO(RoundMarkCriterion entity) {
        if (entity == null) {
            return null;
        }

        return RoundMarkCriterionResponseDTO.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .maxScore(entity.getMaxScore())
                .note(entity.getNote())
                .createdBy(entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null)
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .round(entity.getRound() != null ? mapRoundToDto(entity.getRound()) : null) // Ensure Round is mapped
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
}
