package com.hacof.submission.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionRequestDTO {

    private Long roundId;
    private String status;
    private List<Long> fileIds;
}
