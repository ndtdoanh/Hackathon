package com.hacof.submission.services.impl;

import com.hacof.submission.dtos.request.SubmissionRequestDTO;
import com.hacof.submission.dtos.response.SubmissionResponseDTO;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.services.SubmissionService;
import com.hacof.submission.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionRequestDTO) {
        Submission submission = submissionMapper.toEntity(submissionRequestDTO);
        Submission savedSubmission = submissionRepository.save(submission);
        return submissionMapper.toResponseDTO(savedSubmission);
    }

    @Override
    public SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionRequestDTO) {
        Submission existingSubmission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submissionMapper.updateEntityFromDTO(submissionRequestDTO, existingSubmission);
        Submission updatedSubmission = submissionRepository.save(existingSubmission);
        return submissionMapper.toResponseDTO(updatedSubmission);
    }

    @Override
    public SubmissionResponseDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(submissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubmission(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submissionRepository.delete(submission);
    }
}
