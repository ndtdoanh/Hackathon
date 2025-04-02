package com.hacof.submission.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionRequestDTO {

    private String roundId;
    private String teamId;
    private String status;
    private List<Long> fileIds;
}
