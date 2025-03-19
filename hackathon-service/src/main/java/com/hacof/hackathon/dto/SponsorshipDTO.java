package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class SponsorshipDTO {
    private Long id;
    private Long hackathonId;
    private String sponsorName;
    private String status;
}
