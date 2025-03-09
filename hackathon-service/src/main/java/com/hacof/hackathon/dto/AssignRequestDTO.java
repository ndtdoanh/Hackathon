package com.hacof.hackathon.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AssignRequestDTO {
    @NotEmpty(message = "Judge IDs are mandatory")
    private List<Long> judgeIds;

    @NotEmpty(message = "Mentor IDs are mandatory")
    private List<Long> mentorIds;
}
