package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTeamDTO {
    String id;

    String userId;

    UserDTO user;

    String teamId;

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
