package com.hacof.hackathon.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUrlDTO {
    String id;
    String fileName;
    String fileUrl;
    String fileType;
    int fileSize;
    String submissionId;
    SubmissionDTO submission;
}
