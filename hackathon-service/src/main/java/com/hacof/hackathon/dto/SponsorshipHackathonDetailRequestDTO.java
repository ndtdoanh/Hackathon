package com.hacof.hackathon.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SponsorshipHackathonDetailRequestDTO {
    String sponsorshipHackathonId;
    Double moneySpent;
    String content;
    String status;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;

    List<String> fileUrls;
}

