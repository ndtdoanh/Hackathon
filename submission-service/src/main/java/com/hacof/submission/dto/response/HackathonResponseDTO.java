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
    private Long id;
    private String title;
    private String subTitle;
    private String bannerImageUrl;
    private String description;
    private String information;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxTeams;
    private int minTeamSize;
    private int maxTeamSize;
    private String contact;
    private String category;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
}
