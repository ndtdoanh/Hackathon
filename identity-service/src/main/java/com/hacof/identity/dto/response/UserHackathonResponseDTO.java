package com.hacof.identity.dto.response;

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
