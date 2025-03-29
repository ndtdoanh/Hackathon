package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.entity.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundDTO {
    private String id;
    private HackathonDTO hackathon;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int roundNumber;
    private String roundTitle;
    private RoundStatus status;
    private List<SubmissionDTO> submissions;
    private List<RoundMarkCriterionDTO> roundMarkCriteria;
    // private List<JudgeRoundDTO> judgeRounds;
    private List<TeamRoundDTO> teamRounds;
    private List<RoundLocationDTO> roundLocations;

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
