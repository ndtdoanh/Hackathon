package com.hacof.submission.service;

import java.util.List;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionDetailResponseDTO;

public interface JudgeSubmissionDetailService {

    List<JudgeSubmissionDetailResponseDTO> getAllDetails();

    JudgeSubmissionDetailResponseDTO getDetailById(Long id);

    JudgeSubmissionDetailResponseDTO createDetail(JudgeSubmissionDetailRequestDTO requestDTO);

    JudgeSubmissionDetailResponseDTO updateDetail(Long id, JudgeSubmissionDetailRequestDTO requestDTO);

    boolean deleteDetail(Long id);
}
