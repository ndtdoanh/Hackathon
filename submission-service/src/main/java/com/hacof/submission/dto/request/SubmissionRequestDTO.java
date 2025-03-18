package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmissionRequestDTO {

    private Long roundId;
    private String status;
    private List<Long> fileIds;
}
