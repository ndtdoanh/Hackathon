package com.hacof.hackathon.dto;

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
public class TeamHackathonDTO {
    String id;
    HackathonDTO hackathon;
    String status;
    String createdByUserName;
    LocalDateTime createdAt;
    String lastModifiedByUserName;
    LocalDateTime updatedAt;
}
