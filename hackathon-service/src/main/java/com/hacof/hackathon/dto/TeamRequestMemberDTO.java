package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestMemberDTO {
    String id;
    String teamRequestId;
    UserDTO user;
    String status; // TeamRequestMemberStatus
    String respondedAt; // LocalDateTime

    // Audit fields
    @JsonIgnore
    String createdByUserName;

    @JsonIgnore
    LocalDateTime createdAt;

    @JsonIgnore
    String lastModifiedByUserName; // save username

    @JsonIgnore
    LocalDateTime updatedAt = LocalDateTime.now();
}
