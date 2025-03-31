package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class SponsorshipHackathonDTO {
    private Long id;
    private Long hackathonId;
    private Long sponsorshipId;
    private double totalMoney;
}
