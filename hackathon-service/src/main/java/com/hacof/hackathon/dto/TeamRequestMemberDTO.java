package com.hacof.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestMemberDTO {
    String id;
    String teamRequestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userId;

    UserDTO user;
    String status; // TeamRequestMemberStatus
    String respondedAt; // LocalDateTime

    // Audit fields
    String createdByUserName;

    LocalDateTime createdAt;

    String lastModifiedByUserName; // save username

    LocalDateTime updatedAt = LocalDateTime.now();
}
