package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JudgeDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String email;
}
