package com.hacof.submission.service.impl;

import com.hacof.submission.constant.Status;
import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.entity.FileUrl;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.Submission;
import com.hacof.submission.mapper.SubmissionMapper;
import com.hacof.submission.repository.FileUrlRepository;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.SubmissionRepository;
import com.hacof.submission.service.SubmissionService;
import com.hacof.submission.config.FirebaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files) throws IOException {
        // Convert DTO to entity
        Submission submission = SubmissionMapper.toEntity(submissionDTO, roundRepository); // Now passing roundRepository

        // Save the submission
        submission = submissionRepository.save(submission);

        // Handle file upload to Firebase Storage
        for (MultipartFile file : files) {
//            String fileUrl = uploadFileToFirebase(file);

            // Save file metadata in FileUrl table
            FileUrl fileUrlEntity = new FileUrl();
            fileUrlEntity.setFileName(file.getOriginalFilename());
//            fileUrlEntity.setFileUrl(fileUrl); // If using Firebase, store the file URL here
            fileUrlEntity.setFileType(file.getContentType());
            fileUrlEntity.setFileSize((int) file.getSize());
            fileUrlEntity.setSubmission(submission);

            fileUrlRepository.save(fileUrlEntity);
        }

        return SubmissionMapper.toResponseDTO(submission);
    }

//    public String uploadFileToFirebase(MultipartFile file) throws IOException {
//        // Initialize Firebase
//        StorageReference storageReference = firebaseConfig.getStorageReference();
//
//        // Create a reference to the Firebase Storage location
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        StorageReference fileReference = storageReference.child("submissions/" + fileName);
//
//        // Upload the file to Firebase
//        UploadTask uploadTask = fileReference.putBytes(file.getBytes());
//
//        // Wait for the upload to finish and get the download URL
//        uploadTask.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                TaskSnapshot taskSnapshot = task.getResult();
//                String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//                return downloadUrl;  // Return the file URL
//            } else {
//                throw new IOException("Failed to upload file to Firebase Storage");
//            }
//        });
//    }

    @Override
    public SubmissionResponseDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        return submission != null ? SubmissionMapper.toResponseDTO(submission) : null;
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(SubmissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionDTO) {
        Optional<Submission> optionalSubmission = submissionRepository.findById(id);
        if (!optionalSubmission.isPresent()) {
            return null; // Or handle with appropriate response
        }

        Submission submission = optionalSubmission.get();

        // Fetch Round entity by ID
        Optional<Round> optionalRound = roundRepository.findById(submissionDTO.getRoundId());
        if (!optionalRound.isPresent()) {
            throw new IllegalArgumentException("Round not found for ID: " + submissionDTO.getRoundId());
        }
        Round round = optionalRound.get();

        submission.setRound(round);  // Set the fetched round
        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase())); // Update status
        submissionRepository.save(submission);

        return SubmissionMapper.toResponseDTO(submission);
    }

    @Override
    public boolean deleteSubmission(Long id) {
        if (submissionRepository.existsById(id)) {
            submissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
