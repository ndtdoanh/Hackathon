package com.hacof.submission.services.impl;

import com.hacof.submission.dtos.request.SubmissionfileRequestDTO;
import com.hacof.submission.dtos.response.SubmissionfileResponseDTO;
import com.hacof.submission.entities.Submissionfile;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.Competitionround;
import com.hacof.submission.enums.FileType;
import com.hacof.submission.enums.Status;
import com.hacof.submission.mapper.SubmissionfileMapper;
import com.hacof.submission.repositories.SubmissionfileRepository;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.repositories.CompetitionroundRepository;
import com.hacof.submission.services.FirebaseStorageService;
import com.hacof.submission.services.SubmissionfileService;
import com.hacof.submission.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
public class SubmissionfileServiceImpl implements SubmissionfileService {

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private CompetitionroundRepository competitionroundRepository;

    @Autowired
    private SubmissionfileRepository submissionfileRepository;

    @Autowired
    private SubmissionfileMapper submissionfileMapper;

    @Override
    public SubmissionfileResponseDTO uploadFile(MultipartFile file, Long submissionId, Long roundId) throws IOException {
        // Lấy Submission và Competitionround từ ID
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found!"));

        Competitionround round = competitionroundRepository.findById(roundId)
                .orElseThrow(() -> new RuntimeException("Competition round not found!"));

        // Upload file lên Firebase và lấy URL
        String fileUrl = firebaseStorageService.uploadFile(file);

        // Tạo đối tượng Submissionfile và lưu thông tin
        Submissionfile submissionfile = new Submissionfile();
        submissionfile.setSubmission(submission);
        submissionfile.setRound(round);
        submissionfile.setFileName(file.getOriginalFilename());
        submissionfile.setFileUrl(fileUrl);
        submissionfile.setFileType(FileType.valueOf(file.getContentType().split("/")[0])); // Ví dụ: image/jpeg => FileType.IMAGE
        submissionfile.setStatus(Status.PENDING);
        submissionfile.setUploadedAt(Instant.now());
        submissionfile.setCreatedBy(SecurityUtil.getCurrentUserLogin().orElse("system"));
        submissionfile.setCreatedAt(Instant.now());

        // Lưu vào cơ sở dữ liệu
        Submissionfile savedFile = submissionfileRepository.save(submissionfile);

        // Trả về DTO của file đã upload
        return submissionfileMapper.toResponseDTO(savedFile);
    }
}
