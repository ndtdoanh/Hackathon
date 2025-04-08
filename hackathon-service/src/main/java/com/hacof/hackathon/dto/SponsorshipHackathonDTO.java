package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    // @NotNull(message = "Hackathon ID is required")
    String hackathonId;

    HackathonDTO hackathon;

    // @NotNull(message = "Sponsorship ID is required")
    String sponsorshipId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    SponsorshipDTO sponsorship;

    // @NotNull(message = "Total money is required")
    double totalMoney;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username

    LocalDateTime updatedAt;

    @JsonIgnore
    Set<SponsorshipHackathonDetail> sponsorshipHackathonDetails;
}
