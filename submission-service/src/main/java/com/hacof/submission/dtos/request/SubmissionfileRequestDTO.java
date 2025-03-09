package com.hacof.submission.dtos.request;

import com.hacof.submission.enums.FileType;
import com.hacof.submission.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionfileRequestDTO {

    private Long submissionId;  // Make sure getter/setter are available
    private Long roundId;
    private String fileName;
    private String fileUrl;
    private FileType fileType;  // Ensure FileType enum is used here
    private Status status;      // Ensure Status enum is used here
    private String feedback;

}
