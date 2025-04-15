package com.hacof.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String id;
    String email;
    String username;
    String firstName;
    String lastName;
    String avatarUrl;
    String bio;

    @JsonIgnore
    String country;

    @JsonIgnore
    String city;

    @JsonIgnore
    String birthdate;

    @JsonIgnore
    String phone;

    @JsonIgnore
    String studentId;

    @JsonIgnore
    String university;

    @JsonIgnore
    String linkedinUrl;

    @JsonIgnore
    String githubUrl;

    @JsonIgnore
    List<String> skills;

    @JsonIgnore
    String experienceLevel;

    @JsonIgnore
    String status;

    // Audit fields
    @JsonIgnore
    String createdByUserName; // save username

    @JsonIgnore
    LocalDateTime createdAt;

    @JsonIgnore
    String lastModifiedByUserName; // save username

    @JsonIgnore
    LocalDateTime updatedAt = LocalDateTime.now();
}
