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
public class SponsorshipHackathonDetailRequestDTO {
    String sponsorshipHackathonId;
    Double moneySpent;
    String content;
    String status;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;

    List<String> fileUrls;
}
