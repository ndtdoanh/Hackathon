package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SponsorshipHackathonDTO {
    String id;

    @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    @NotNull(message = "Sponsorship ID is required")
    String sponsorshipId;

    @NotNull(message = "Total money is required")
    double totalMoney;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;

    @JsonIgnore
    Set<SponsorshipHackathonDetail> sponsorshipHackathonDetails;
}
