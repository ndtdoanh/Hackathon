package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SponsorshipHackathonDetailResponseDTO {
    String id;
    String sponsorshipHackathonId;
    SponsorshipHackathonDTO sponsorshipHackathon;
    Double moneySpent;
    String content;
    String status;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName;
    LocalDateTime updatedAt;

    List<FileUrlResponse> fileUrls;
}
