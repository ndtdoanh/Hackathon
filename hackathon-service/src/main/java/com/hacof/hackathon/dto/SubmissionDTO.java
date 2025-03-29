package com.hacof.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private String id;
    private String title;
    private String description;
    private String fileUrl;
    private String status;
}
