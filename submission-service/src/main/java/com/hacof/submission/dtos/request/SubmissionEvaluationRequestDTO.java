package com.hacof.submission.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SubmissionEvaluationRequestDTO {
    private Long submissionId;  // Submission mà bài đánh giá thuộc về
    private Long judgeId;       // Giám khảo thực hiện đánh giá
    private String feedback;    // Nhận xét của giám khảo
    private Instant evaluatedAt; // Thời gian đánh giá
}
