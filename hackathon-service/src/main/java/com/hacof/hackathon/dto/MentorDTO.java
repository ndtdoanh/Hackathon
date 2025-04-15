package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Expertise is mandatory")
    private String expertise;

    @NotBlank(message = "Email is mandatory")
    private String email;
}
