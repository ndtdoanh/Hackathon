package com.hacof.hackathon.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HackathonDTO {
    private Long id;
    private String name;
    private String bannerImageUrl;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long organizerId;
    private String organizerName; // New field to include organizer's name
    private String createdBy;
    private String lastUpdatedBy;
}
