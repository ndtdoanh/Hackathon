package com.hacof.identity.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserHackathonResponseDTO {
    String id;
    UserSimpleResponse user;
    HackathonSimpleResponse hackathon;
    String role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
