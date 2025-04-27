package com.hacof.hackathon.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndividualRegistrationBulkRequestDTO {
    String hackathonId;
    String status;
    String createdByUserId;


}