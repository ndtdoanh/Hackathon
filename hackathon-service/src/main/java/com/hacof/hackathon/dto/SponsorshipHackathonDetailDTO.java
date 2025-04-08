package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hacof.hackathon.entity.FileUrl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SponsorshipHackathonDetailDTO {
    String id;

    // @NotNull(message = "SponsorshipHackathonId is required")
    String sponsorshipHackathonId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    SponsorshipHackathonDTO sponsorshipHackathon;

    @NotNull(message = "MoneySpent is required")
    Double moneySpent;

    @NotNull(message = "Content is required")
    String content;

    @NotNull(message = "Status is required")
    String status;

    @NotNull(message = "TimeFrom is required")
    LocalDateTime timeFrom;

    @NotNull(message = "TimeTo is required")
    LocalDateTime timeTo;

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt;

    @JsonIgnore
    List<FileUrl> fileUrls = new ArrayList<>();
}
