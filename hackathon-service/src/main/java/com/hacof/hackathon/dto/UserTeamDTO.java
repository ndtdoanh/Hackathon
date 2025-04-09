package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTeamDTO {
    String id;

    @JsonIgnore
    String userId;
    @JsonIgnore
    UserDTO user;
    @JsonIgnore
    String teamId;
    @JsonIgnore
    TeamDTO team;

    @JsonIgnore
    private LocalDateTime createdDate;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private LocalDateTime updatedDate;

    @JsonIgnore
    private String updatedBy;
}
