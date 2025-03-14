package com.hacof.submission.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dtos.request.SubmissionEvaluationRequestDTO;
import com.hacof.submission.dtos.response.SubmissionEvaluationResponseDTO;
import com.hacof.submission.entities.EvaluationScores;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.Submissionevaluation;
import com.hacof.submission.entities.User;
import com.hacof.submission.mapper.SubmissionEvaluationMapper;
import com.hacof.submission.repositories.EvaluationScoreRepository;
import com.hacof.submission.repositories.SubmissionEvaluationRepository;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.repositories.UserRepository;
import com.hacof.submission.services.SubmissionEvaluationService;
import com.hacof.submission.utils.SecurityUtil;

@Service
public class SubmissionEvaluationServiceImpl implements SubmissionEvaluationService {

    @Autowired
    private SubmissionEvaluationRepository submissionevaluationRepository;

    @Autowired
    private EvaluationScoreRepository evaluationScoreRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionEvaluationMapper mapper;

    @Override
    public List<SubmissionEvaluationResponseDTO> getAllEvaluations() {
        List<Submissionevaluation> evaluations = submissionevaluationRepository.findAll();
        return evaluations.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public SubmissionEvaluationResponseDTO getEvaluationById(Long id) {
        Submissionevaluation evaluation = submissionevaluationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found!"));
        return mapper.toResponseDTO(evaluation);
    }

    @Override
    public SubmissionEvaluationResponseDTO createEvaluation(SubmissionEvaluationRequestDTO evaluationRequestDTO) {
        // Tính tổng điểm từ bảng EvaluationScores
        Float totalScore =
                (float) evaluationScoreRepository.findBySubmissionId(evaluationRequestDTO.getSubmissionId()).stream()
                        .mapToDouble(EvaluationScores::getScore)
                        .sum();

        Submission submission = submissionRepository
                .findById(evaluationRequestDTO.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission not found!"));
        User judge = userRepository
                .findById(evaluationRequestDTO.getJudgeId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Submissionevaluation submissionEvaluation = new Submissionevaluation();
        submissionEvaluation.setSubmission(submission);
        submissionEvaluation.setJudge(judge);
        submissionEvaluation.setScore(totalScore);
        submissionEvaluation.setFeedback(evaluationRequestDTO.getFeedback());
        submissionEvaluation.setEvaluatedAt(Instant.now());
        submissionEvaluation.setCreatedAt(Instant.now());
        submissionEvaluation.setCreatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

        submissionevaluationRepository.save(submissionEvaluation);
        return new SubmissionEvaluationResponseDTO(submissionEvaluation);
    }

    @Override
    public SubmissionEvaluationResponseDTO updateEvaluation(
            Long id, SubmissionEvaluationRequestDTO evaluationRequestDTO) {
        Submission submission = submissionRepository
                .findById(evaluationRequestDTO.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission not found!"));
        User judge = userRepository
                .findById(evaluationRequestDTO.getJudgeId())
                .orElseThrow(() -> new RuntimeException("Judge not found!"));

        //        // Recalculate the total score from EvaluationScores for the given submission
        //        Float totalScore = (float)
        // evaluationScoreRepository.findBySubmissionId(evaluationRequestDTO.getSubmissionId())
        //                .stream()
        //                .mapToDouble(EvaluationScores::getScore)
        //                .sum();
        //
        //        // Validate if the total score is not greater than the max score defined by the Evaluation Criteria
        //        // Assuming max score is retrieved from the EvaluationCriteria
        //        Float maxScore = getMaxScoreFromEvaluationCriteria(submission); // A method to fetch max score logic
        //        if (totalScore > maxScore) {
        //            throw new RuntimeException("Total score exceeds the maximum score!");
        //        }
        Optional<Submissionevaluation> existingEvaluation = submissionevaluationRepository.findById(id);

        if (existingEvaluation.isPresent()) {
            Submissionevaluation evaluation = existingEvaluation.get();

            evaluation.setFeedback(evaluationRequestDTO.getFeedback());
            // Cập nhật điểm tổng thể nếu cần
            Float totalScore = (float)
                    evaluationScoreRepository.findBySubmissionId(evaluationRequestDTO.getSubmissionId()).stream()
                            .mapToDouble(EvaluationScores::getScore)
                            .sum();
            evaluation.setScore(totalScore);
            evaluation.setUpdatedAt(Instant.now());
            evaluation.setUpdatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));

            Submissionevaluation updatedEvaluation = submissionevaluationRepository.save(evaluation);
            return mapper.toResponseDTO(updatedEvaluation);
        } else {
            throw new RuntimeException("Evaluation not found with ID: " + id);
        }
    }

    @Override
    public boolean deleteEvaluation(Long id) {
        Optional<Submissionevaluation> evaluation = submissionevaluationRepository.findById(id);
        if (evaluation.isPresent()) {
            submissionevaluationRepository.delete(evaluation.get());
            return true;
        }
        return false;
    }
}
