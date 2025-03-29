package com.hacof.submission.dto.response;

import java.time.LocalDateTime;

import lombok.*;
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
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdBy;
}
