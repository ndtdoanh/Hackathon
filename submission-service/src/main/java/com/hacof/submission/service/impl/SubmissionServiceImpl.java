package com.hacof.submission.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.config.FirebaseConfig;
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


    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "image/jpeg", // JPEG images
            "image/png", // PNG images
            "application/pdf", // PDF files
            "application/msword", // .doc files (Microsoft Word)
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx files (Microsoft Word)
            "application/vnd.ms-excel", // .xls files (Microsoft Excel)
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xlsx files (Microsoft Excel)
            "application/vnd.ms-powerpoint", // .ppt files (Microsoft PowerPoint)
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .pptx files (Microsoft
            // PowerPoint)
            "text/plain" // .txt files (Text files)
            );
    @Autowired
    private S3Service s3Service;

    @Override
    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
            throws IOException {
        Submission submission = SubmissionMapper.toEntity(submissionDTO, roundRepository);
        submission.setSubmittedAt(LocalDateTime.now());
        submission = submissionRepository.save(submission);

        // Handle file uploads to S3 and store file metadata in FileUrl
        for (MultipartFile file : files) {
            String fileType = file.getContentType();
            if (!ALLOWED_FILE_TYPES.contains(fileType)) {
                throw new IllegalArgumentException("File type not allowed: " + fileType);
            }

            // Upload file to S3 and get the file URL
            String fileUrl = s3Service.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getSize());

            // Create a new FileUrl entity for the uploaded file
            FileUrl fileUrlEntity = new FileUrl();
            fileUrlEntity.setFileName(file.getOriginalFilename());
            fileUrlEntity.setFileUrl(fileUrl); // Store the S3 file URL
            fileUrlEntity.setFileType(file.getContentType());
            fileUrlEntity.setFileSize((int) file.getSize());
            fileUrlEntity.setSubmission(submission);

            fileUrlRepository.save(fileUrlEntity);
            submission.getFileUrls().add(fileUrlEntity);
        }

        submissionRepository.save(submission);
        return submissionMapper.toResponseDTO(submission);
    }


//    @Override
//    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
//            throws IOException {
//        Submission submission = SubmissionMapper.toEntity(submissionDTO, roundRepository);
//        submission.setSubmittedAt(LocalDateTime.now());
//        submission = submissionRepository.save(submission);
//
//        // Handle file uploads and store file metadata in FileUrl
//        for (MultipartFile file : files) {
//            String fileType = file.getContentType();
//            if (!ALLOWED_FILE_TYPES.contains(fileType)) {
//                throw new IllegalArgumentException("File type not allowed: " + fileType);
//            }
//
//            // Generate a unique file name using UUID
//            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//
//            try {
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//
//                // Create the file path and copy the file to the directory
//                Path filePath = uploadPath.resolve(fileName);
//                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                // Create a new FileUrl entity for the uploaded file
//                FileUrl fileUrlEntity = new FileUrl();
//                fileUrlEntity.setFileName(file.getOriginalFilename());
//                fileUrlEntity.setFileUrl("/" + UPLOAD_DIR + fileName);
//                fileUrlEntity.setFileType(file.getContentType());
//                fileUrlEntity.setFileSize((int) file.getSize());
//                fileUrlEntity.setSubmission(submission);
//
//                fileUrlRepository.save(fileUrlEntity);
//                submission.getFileUrls().add(fileUrlEntity);
//
//            } catch (IOException e) {
//                throw new IllegalArgumentException("Failed to store file", e);
//            }
//        }
//
//        submissionRepository.save(submission);
//        return submissionMapper.toResponseDTO(submission);
//    }

    //    @Override
    //    public SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
    // throws IOException {
    //        Submission submission = SubmissionMapper.toEntity(submissionDTO, roundRepository);
    //
    //        // Save the submission
    //        submission = submissionRepository.save(submission);
    //
    //        // Handle file upload to Firebase Storage
    //        for (MultipartFile file : files) {
    ////            String fileUrl = uploadFileToFirebase(file);
    //
    //            // Save file metadata in FileUrl table
    //            FileUrl fileUrlEntity = new FileUrl();
    //            fileUrlEntity.setFileName(file.getOriginalFilename());
    ////            fileUrlEntity.setFileUrl(fileUrl); // If using Firebase, store the file URL here
    //            fileUrlEntity.setFileType(file.getContentType());
    //            fileUrlEntity.setFileSize((int) file.getSize());
    //            fileUrlEntity.setSubmission(submission);
    //
    //            fileUrlRepository.save(fileUrlEntity);
    //        }
    //
    //        return SubmissionMapper.toResponseDTO(submission);
    //    }

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
        Optional<Submission> submissionOpt = submissionRepository.findById(id);
        if (!submissionOpt.isPresent()) {
            throw new IllegalArgumentException("Submission not found with ID " + id);
        }
        return submissionMapper.toResponseDTO(submissionOpt.get());
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream().map(submissionMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public SubmissionResponseDTO updateSubmission(
            Long id, SubmissionRequestDTO submissionDTO, List<MultipartFile> files) {
        Optional<Submission> optionalSubmission = submissionRepository.findById(id);
        if (!optionalSubmission.isPresent()) {
            throw new IllegalArgumentException("Submission not found for ID: " + id);
        }

        Submission submission = optionalSubmission.get();

        Optional<Round> optionalRound = roundRepository.findById(submissionDTO.getRoundId());
        if (!optionalRound.isPresent()) {
            throw new IllegalArgumentException("Round not found for ID: " + submissionDTO.getRoundId());
        }
        Round round = optionalRound.get();

        submission.setRound(round);
        submission.setStatus(Status.valueOf(submissionDTO.getStatus().toUpperCase()));
        submission.setSubmittedAt(LocalDateTime.now());

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                FileUrl fileUrl = new FileUrl();
                // Save the file (upload logic not shown here)
                fileUrl.setFileName(file.getOriginalFilename());
                fileUrl.setFileUrl("some file url");
                fileUrl.setFileType(file.getContentType());
                fileUrl.setFileSize((int) file.getSize());
                fileUrl.setSubmission(submission);

                // Save FileUrl to the submission
                submission.getFileUrls().add(fileUrl);
            }
        }

        submissionRepository.save(submission);
        return submissionMapper.toResponseDTO(submission);
    }

    @Override
    public boolean deleteSubmission(Long id) {
        Optional<Submission> submissionOpt = submissionRepository.findById(id);
        if (!submissionOpt.isPresent()) {
            throw new IllegalArgumentException("Submission not found with ID " + id);
        }
        submissionRepository.deleteById(id);
        return true;
    }
}
