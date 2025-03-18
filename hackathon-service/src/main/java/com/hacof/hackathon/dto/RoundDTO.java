package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.entity.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundDTO {
    private long id;
    private long hackathonId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int roundNumber;
    private String roundTitle;
    private String status;
    private List<Long> submissionIds;
    private List<Long> roundMarkCriterionIds;
    private List<Long> judgeRoundIds;
    private List<Long> teamRoundIds;
    private List<Long> roundLocationIds;
}
