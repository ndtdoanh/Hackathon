package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamDTO {
    @JsonIgnore
    private Long id;
    private Long userId;
    private Long teamId;
    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private LocalDateTime updatedDate;
    @JsonIgnore
    private String updatedBy;
}
