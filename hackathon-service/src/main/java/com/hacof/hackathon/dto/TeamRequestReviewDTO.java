package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestStatus;

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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TeamRequestReviewDTO {
    String requestId;
    TeamRequestStatus status;
    String note;
}
