package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SponsorshipDTO {
    String id;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Brand is required")
    String brand;

    @NotBlank(message = "Content is required")
    String content;

    @NotNull(message = "Money is required")
    double money;

    LocalDateTime timeFrom;
    LocalDateTime timeTo;

    @NotBlank(message = "Status is required")
    String status;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;

    @JsonIgnore
    Set<SponsorshipHackathonDTO> sponsorshipHackathons;
}
