package com.hacof.submission.mapper;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JudgeSubmissionMapper {

    // Chuyển từ DTO (request) sang Entity (JudgeSubmission) cho việc phân công ban đầu
    public JudgeSubmission toEntity(JudgeSubmissionRequestDTO dto) {
        JudgeSubmission entity = new JudgeSubmission();

        // Set Judge and Submission
        User judge = new User();
        judge.setId(dto.getJudgeId());  // Thiết lập người chấm từ judgeId
        entity.setJudge(judge);

        Submission submission = new Submission();
        submission.setId(dto.getSubmissionId());  // Thiết lập submission từ submissionId
        entity.setSubmission(submission);

        entity.setNote(dto.getNote()); // Note from DTO

        // Initially setting the score to 0, since it will be recalculated later
        entity.setScore(0);

        return entity;
    }

    // Chuyển từ Entity (JudgeSubmission) sang DTO (JudgeSubmissionResponseDTO)
    public JudgeSubmissionResponseDTO toResponseDTO(JudgeSubmission entity) {
        return new JudgeSubmissionResponseDTO(entity);
    }

    // Chuyển từ UpdateScoreDTO (request) sang Entity (JudgeSubmission) để cập nhật điểm và ghi chú
    public JudgeSubmission toEntity(UpdateScoreRequest dto) {
        JudgeSubmission entity = new JudgeSubmission();

        // Fetch the JudgeSubmission by ID
        entity.setId(dto.getJudgeSubmissionId());  // Set the existing judgeSubmissionId
        entity.setNote(dto.getNote()); // Update the note

        // The score will be recalculated automatically in the service layer

        return entity;
    }

    // Chuyển danh sách Entity sang danh sách DTO
    public List<JudgeSubmissionResponseDTO> toResponseDTOList(List<JudgeSubmission> entities) {
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
