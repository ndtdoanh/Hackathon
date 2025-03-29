package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SponsorshipHackathonDetailDTO {
    private Long id;
    private Long sponsorshipHackathonId;
    private double moneySpent;
    private String content;
    private String status;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
}
