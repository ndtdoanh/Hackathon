package com.hacof.identity.dto.response;

import com.hacof.identity.constant.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonResponse {
    String id;
    String title;
    String bannerImageUrl;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Status status;
    List<HackathonResultResponse> hackathonResults;
}
