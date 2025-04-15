package com.hacof.submission.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmissionRequestDTO {

    private String roundId;
    private String teamId;
    private String status;
    private List<Long> fileIds;
}
