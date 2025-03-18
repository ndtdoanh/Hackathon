package com.hacof.submission.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionResponseDTO {

    private long id;
    private Long roundId;
    private String status;
    private LocalDateTime submittedAt;
    private List<FileUrlResponseDTO> fileUrls;
}
