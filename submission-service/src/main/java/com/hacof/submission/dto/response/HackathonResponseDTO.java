package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonResponseDTO {
    String id;
    String title;
    String subTitle;
    String bannerImageUrl;
    String description;
    String information;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int maxTeams;
    int minTeamSize;
    int maxTeamSize;
    String contact;
    String category;
    String status;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createdByUserName;
}
