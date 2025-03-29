package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SponsorshipDTO {
    private Long id;
    private String name;
    private String brand;
    private String content;
    private double money;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private String status;
}
