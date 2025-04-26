package com.hacof.submission.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.constant.Status;
import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.entity.FileUrl;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.Submission;
import com.hacof.submission.entity.Team;
import com.hacof.submission.mapper.SubmissionMapper;
import com.hacof.submission.repository.FileUrlRepository;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.SubmissionRepository;
import com.hacof.submission.repository.TeamRepository;
import com.hacof.submission.service.SubmissionService;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private S3Service s3Service;

//    private static final List<String> ALLOWED_FILE_TYPES = List.of(
//            "image/jpeg",
//            "image/png",
//            "application/pdf",
//            "application/msword",
//            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
//            "application/vnd.ms-excel",
//            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//            "application/vnd.ms-powerpoint",
//            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
//            "text/plain");

    @Transactional
    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
            throws IOException {
        Round round = roundRepository
                .findById(Long.valueOf(submissionDTO.getRoundId()))
                .orElseThrow(
                        () -> new IllegalArgumentException("Round not found with ID " + submissionDTO.getRoundId()));

        Team team = teamRepository
                .findById(Long.valueOf(submissionDTO.getTeamId()))
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID " + submissionDTO.getTeamId()));

        LocalDateTime now = LocalDateTime.now();
        if (round.getStartTime() != null && now.isBefore(round.getStartTime())) {
            throw new IllegalArgumentException("Submission is not allowed before the round starts.");
        }
        if (round.getEndTime() != null && now.isAfter(round.getEndTime())) {
            throw new IllegalArgumentException("Submission is not allowed after the round ends.");
        }

        Submission submission = new Submission();
        submission.setRound(round);
        submission.setTeam(team);
        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase()));
        submission.setSubmittedAt(LocalDateTime.now());

        submission = submissionRepository.save(submission);

        if (files != null && !files.isEmpty()) {
            if (files.size() > 10) {
                throw new IllegalArgumentException("Maximum 10 files allowed");
            }
            for (MultipartFile file : files) {
                processAndSaveFile(file, submission);
            }
        }

        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public SubmissionResponseDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID " + id));
        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream().map(submissionMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SubmissionResponseDTO updateSubmission(
            Long id, SubmissionRequestDTO submissionDTO, List<MultipartFile> files) throws IOException {
        Submission submission = submissionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found for ID: " + id));

        Round round = roundRepository
                .findById(Long.valueOf(submissionDTO.getRoundId()))
                .orElseThrow(
                        () -> new IllegalArgumentException("Round not found for ID: " + submissionDTO.getRoundId()));
        submission.setRound(round);

        LocalDateTime now = LocalDateTime.now();
        if (round.getStartTime() != null && now.isBefore(round.getStartTime())) {
            throw new IllegalArgumentException("Submission update is not allowed before the round starts.");
        }
        if (round.getEndTime() != null && now.isAfter(round.getEndTime())) {
            throw new IllegalArgumentException("Submission update is not allowed after the round ends.");
        }

        Team team = teamRepository
                .findById(Long.valueOf(submissionDTO.getTeamId()))
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID " + submissionDTO.getTeamId()));
        submission.setTeam(team);
        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase()));
        submission.setSubmittedAt(LocalDateTime.now());

        if (files != null && !files.isEmpty()) {
            if (files.size() > 10) {
                throw new IllegalArgumentException("Maximum 10 files allowed");
            }

            for (MultipartFile file : files) {
                processAndSaveFile(file, submission);
            }
        }

        submissionRepository.save(submission);
        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public boolean deleteSubmission(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Submission not found with ID " + id);
        }
        submissionRepository.deleteById(id);
        return true;
    }

    private static final long MAX_FILE_SIZE = 15 * 1024 * 1024;

    private void processAndSaveFile(MultipartFile file, Submission submission) throws IOException {
//        String fileType = file.getContentType();
//        if (!ALLOWED_FILE_TYPES.contains(fileType)) {
//            throw new IllegalArgumentException("File type not allowed: " + fileType);
//        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds the 15MB limit.");
        }

        String fileUrl = s3Service.uploadFile(
                file.getInputStream(), file.getOriginalFilename(), file.getSize(), file.getContentType());

        FileUrl fileUrlEntity = new FileUrl();
        fileUrlEntity.setFileName(file.getOriginalFilename());
        fileUrlEntity.setFileUrl(fileUrl);
        fileUrlEntity.setFileType(file.getContentType());
        fileUrlEntity.setFileSize((int) file.getSize());
        fileUrlEntity.setSubmission(submission);

        fileUrlRepository.save(fileUrlEntity);
        submission.getFileUrls().add(fileUrlEntity);
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionsByRoundAndCreatedBy(Long roundId, String createdByUsername) {
        List<Submission> submissions =
                submissionRepository.findByRoundIdAndCreatedByUsername(roundId, createdByUsername);
        return submissions.stream().map(submissionMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<SubmissionResponseDTO> getSubmissionsByTeamAndRound(Long teamId, Long roundId) {
        List<Submission> submissions = submissionRepository.findByTeamIdAndRoundId(teamId, roundId);
        return submissions.stream().map(submissionMapper::toResponseDTO).collect(Collectors.toList());
    }
}
