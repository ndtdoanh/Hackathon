package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class IndividualRegistrationRequestDTO {
    private String id;
    private String hackathonId;
    private String status;
    private String reviewedById;
}
