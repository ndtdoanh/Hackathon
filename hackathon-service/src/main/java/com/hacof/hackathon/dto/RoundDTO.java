package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.entity.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundDTO {
    String id;

    @NotBlank(message = "Hackathon ID is required")
    String hackathonId;

    @NotNull(message = "Start Time is required")
    @FutureOrPresent(message = "Start Time must be in the present or future")
    LocalDateTime startTime;

    @NotNull(message = "End Time is required")
    @FutureOrPresent(message = "End Time must be in the present or future")
    LocalDateTime endTime;

    @NotNull(message = "Round Number is required")
    @Min(value = 1, message = "Round Number must be greater than 0")
    int roundNumber;

    @NotBlank(message = "Round Title is required")
    String roundTitle;

    @NotNull(message = "Round Status is required")
    RoundStatus status;

    @JsonIgnore
    List<SubmissionDTO> submissions;

    @JsonIgnore
    List<RoundMarkCriterionDTO> roundMarkCriteria;
    // private List<JudgeRoundDTO> judgeRounds;
    @JsonIgnore
    List<TeamRoundDTO> teamRounds;

    @JsonIgnore
    List<RoundLocationDTO> roundLocations;

    // Audit fields
    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;

    @AssertTrue(message = "Start Time must be before End Time")
    public boolean isStartTimeBeforeEndTime() {
        return startTime.isBefore(endTime);
    }
}
