package com.hacof.submission.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import com.hacof.submission.service.impl.S3Service;
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

    private static final List<String> ALLOWED_FILE_TYPES = List.of(
            "image/jpeg", "image/png", "application/pdf",
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "text/plain"
    );

    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
            throws IOException {
        // Kiểm tra xem Round có tồn tại không
        Round round = roundRepository.findById(submissionDTO.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found with ID " + submissionDTO.getRoundId()));

        // Kiểm tra xem Team có tồn tại không
        Team team = teamRepository.findById(submissionDTO.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID " + submissionDTO.getTeamId()));

        // Tạo entity Submission
        Submission submission = new Submission();
        submission.setRound(round);
        submission.setTeam(team);
        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase()));
        submission.setSubmittedAt(LocalDateTime.now());

        submission = submissionRepository.save(submission);

        // Xử lý upload file lên S3
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                processAndSaveFile(file, submission);
            }
        }

        return submissionMapper.toResponseDTO(submission);
    }


    @Override
    public SubmissionResponseDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID " + id));
        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(submissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
            throws IOException {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found for ID: " + id));

        // Cập nhật thông tin vòng đấu và đội
        Round round = roundRepository.findById(submissionDTO.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found for ID: " + submissionDTO.getRoundId()));
        submission.setRound(round);

        Team team = teamRepository.findById(submissionDTO.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID " + submissionDTO.getTeamId()));
        submission.setTeam(team);

        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase()));
        submission.setSubmittedAt(LocalDateTime.now());

        // Xử lý upload file lên S3 và lưu metadata vào database
        if (files != null && !files.isEmpty()) {
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
        String fileType = file.getContentType();
        if (!ALLOWED_FILE_TYPES.contains(fileType)) {
            throw new IllegalArgumentException("File type not allowed: " + fileType);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds the 15MB limit.");
        }

        // Upload file lên S3
        String fileUrl = s3Service.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getSize());

        // Lưu thông tin file vào database
        FileUrl fileUrlEntity = new FileUrl();
        fileUrlEntity.setFileName(file.getOriginalFilename());
        fileUrlEntity.setFileUrl(fileUrl);
        fileUrlEntity.setFileType(file.getContentType());
        fileUrlEntity.setFileSize((int) file.getSize());
        fileUrlEntity.setSubmission(submission);

        fileUrlRepository.save(fileUrlEntity);
        submission.getFileUrls().add(fileUrlEntity);
    }
}
