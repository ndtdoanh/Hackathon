package com.hacof.submission.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.entity.*;

@Component
public class JudgeSubmissionMapper {

    public JudgeSubmission toEntity(JudgeSubmissionRequestDTO dto) {
        JudgeSubmission entity = new JudgeSubmission();

        User judge = new User();
        judge.setId(dto.getJudgeId());
        entity.setJudge(judge);

        Submission submission = new Submission();
        submission.setId(dto.getSubmissionId());
        entity.setSubmission(submission);

        entity.setNote(dto.getNote());

        entity.setScore(0);

        return entity;
    }

    public JudgeSubmissionResponseDTO toResponseDTO(JudgeSubmission entity) {
        return new JudgeSubmissionResponseDTO(entity);
    }

    public JudgeSubmission toEntity(UpdateScoreRequest dto) {
        JudgeSubmission entity = new JudgeSubmission();

        entity.setId(dto.getJudgeSubmissionId()); // Set the existing judgeSubmissionId from the request
        entity.setNote(dto.getNote()); // Update the note

        return entity;
    }

    public List<JudgeSubmissionResponseDTO> toResponseDTOList(List<JudgeSubmission> entities) {
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
