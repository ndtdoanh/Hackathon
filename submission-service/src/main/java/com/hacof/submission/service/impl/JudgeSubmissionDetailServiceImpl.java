package com.hacof.submission.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;
import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.mapper.JudgeSubmissionDetailMapper;
import com.hacof.submission.repository.JudgeSubmissionDetailRepository;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.RoundMarkCriterionRepository;
import com.hacof.submission.service.JudgeSubmissionDetailService;

@Service
public class JudgeSubmissionDetailServiceImpl implements JudgeSubmissionDetailService {

    @Autowired
    private JudgeSubmissionDetailRepository detailRepository;

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private RoundMarkCriterionRepository roundMarkCriterionRepository;

    @Autowired
    private JudgeSubmissionDetailMapper mapper;

    @Override
    public List<JudgeSubmissionDetailResponseDTO> getAllDetails() {
        List<JudgeSubmissionDetail> details = detailRepository.findAll();
        return details.stream()
                .map(detail -> JudgeSubmissionDetailMapper.toResponseDTO(detail)) // Gọi phương thức đúng từ Mapper
                .collect(Collectors.toList());
    }

    @Override
    public JudgeSubmissionDetailResponseDTO getDetailById(Long id) {
        Optional<JudgeSubmissionDetail> detail = detailRepository.findById(id);
        if (!detail.isPresent()) {
            throw new IllegalArgumentException("Judge Submission Detail with ID " + id + " not found.");
        }
        return mapper.toResponseDTO(detail.get());
    }

    @Override
    public JudgeSubmissionDetailResponseDTO createDetail(JudgeSubmissionDetailRequestDTO requestDTO) {
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(requestDTO.getJudgeSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Judge Submission not found"));
        RoundMarkCriterion roundMarkCriterion = roundMarkCriterionRepository
                .findById(requestDTO.getRoundMarkCriterionId())
                .orElseThrow(() -> new IllegalArgumentException("Round Mark Criterion not found"));
        if (requestDTO.getScore() > roundMarkCriterion.getMaxScore()) {
            throw new IllegalArgumentException(
                    "Score cannot be greater than Max Score (" + roundMarkCriterion.getMaxScore() + ")");
        }
        JudgeSubmissionDetail entity = mapper.toEntity(requestDTO);
        entity.setJudgeSubmission(judgeSubmission);
        entity.setRoundMarkCriterion(roundMarkCriterion);

        JudgeSubmissionDetail savedDetail = detailRepository.save(entity);

        // Cập nhật điểm tổng trong JudgeSubmission
        int newTotalScore = judgeSubmission.getScore() + savedDetail.getScore();
        judgeSubmission.setScore(newTotalScore);
        judgeSubmissionRepository.save(judgeSubmission);

        return mapper.toResponseDTO(savedDetail);
    }

    @Override
    public JudgeSubmissionDetailResponseDTO updateDetail(Long id, JudgeSubmissionDetailRequestDTO requestDTO) {
        JudgeSubmissionDetail existingDetail = detailRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Judge Submission Detail not found"));

        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(requestDTO.getJudgeSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Judge Submission not found"));
        RoundMarkCriterion roundMarkCriterion = roundMarkCriterionRepository
                .findById(requestDTO.getRoundMarkCriterionId())
                .orElseThrow(() -> new IllegalArgumentException("Round Mark Criterion not found"));

        if (requestDTO.getScore() > roundMarkCriterion.getMaxScore()) {
            throw new IllegalArgumentException(
                    "Score cannot be greater than Max Score (" + roundMarkCriterion.getMaxScore() + ")");
        }

        // Lưu lại điểm cũ của JudgeSubmission để cập nhật sau khi thay đổi điểm
        int oldScore = existingDetail.getScore();

        existingDetail.setScore(requestDTO.getScore());
        existingDetail.setNote(requestDTO.getNote());
        existingDetail.setJudgeSubmission(judgeSubmission);
        existingDetail.setRoundMarkCriterion(roundMarkCriterion);

        JudgeSubmissionDetail updatedDetail = detailRepository.save(existingDetail);

        // Cập nhật điểm tổng trong JudgeSubmission (cộng điểm mới, trừ điểm cũ)
        int newTotalScore = judgeSubmission.getScore() - oldScore + updatedDetail.getScore();
        judgeSubmission.setScore(newTotalScore);
        judgeSubmissionRepository.save(judgeSubmission);

        return mapper.toResponseDTO(updatedDetail);
    }

    @Override
    public boolean deleteDetail(Long id) {
        JudgeSubmissionDetail existingDetail = detailRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Judge Submission Detail with ID " + id + " not found"));

        detailRepository.delete(existingDetail);
        return true;
    }
}
