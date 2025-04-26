package com.hacof.hackathon.dto;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUrlResponse {
    String id;
    String fileName;
    String fileUrl;
    String fileType;
    int fileSize;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
