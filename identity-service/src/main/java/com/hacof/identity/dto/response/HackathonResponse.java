package com.hacof.identity.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.hacof.identity.constant.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonResponse {
    String id;
    String bannerImageUrl;
    String title;
    LocalDate startDate;
    LocalDate endDate;
    Status status;
    List<HackathonResultResponse> hackathonResults;
}
