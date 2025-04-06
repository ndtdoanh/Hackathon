package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

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
    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
