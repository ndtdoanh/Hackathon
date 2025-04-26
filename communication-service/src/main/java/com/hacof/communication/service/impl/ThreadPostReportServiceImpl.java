package com.hacof.communication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.constant.ThreadPostReportStatus;
import com.hacof.communication.dto.request.ThreadPostReportReviewRequestDTO;
import com.hacof.communication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostReport;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.ThreadPostReportMapper;
import com.hacof.communication.repository.ThreadPostReportRepository;
import com.hacof.communication.repository.ThreadPostRepository;
import com.hacof.communication.service.ThreadPostReportService;
import com.hacof.communication.util.AuditContext;

@Service
public class ThreadPostReportServiceImpl implements ThreadPostReportService {

    @Autowired
    private ThreadPostReportRepository threadPostReportRepository;

    @Autowired
    private ThreadPostRepository threadPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ThreadPostReportResponseDTO createThreadPostReport(ThreadPostReportRequestDTO requestDTO) {
        Long threadPostId = Long.parseLong(requestDTO.getThreadPostId());
        ThreadPost threadPost = threadPostRepository
                .findById(threadPostId)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + threadPostId));

        ThreadPostReport threadPostReport = ThreadPostReportMapper.toEntity(requestDTO, threadPost);
        threadPostReport = threadPostReportRepository.save(threadPostReport);

        return ThreadPostReportMapper.toResponseDTO(threadPostReport);
    }

    @Override
    public List<ThreadPostReportResponseDTO> getReportsByThreadPostId(Long threadPostId) {
        List<ThreadPostReport> reports = threadPostReportRepository.findByThreadPostId(threadPostId);
        return reports.stream().map(ThreadPostReportMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ThreadPostReportResponseDTO getThreadPostReport(Long id) {
        ThreadPostReport threadPostReport = threadPostReportRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPostReport not found with id " + id));
        return ThreadPostReportMapper.toResponseDTO(threadPostReport);
    }

    @Override
    public ThreadPostReportResponseDTO updateThreadPostReport(Long id, ThreadPostReportRequestDTO requestDTO) {
        ThreadPostReport threadPostReport = threadPostReportRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPostReport not found with id " + id));

        threadPostReport.setReason(requestDTO.getReason());

        threadPostReport = threadPostReportRepository.save(threadPostReport);
        return ThreadPostReportMapper.toResponseDTO(threadPostReport);
    }

    @Override
    public void deleteThreadPostReport(Long id) {
        if (!threadPostReportRepository.existsById(id)) {
            throw new IllegalArgumentException("ThreadPostReport not found with id " + id);
        }
        threadPostReportRepository.deleteById(id);
    }

    @Override
    public ThreadPostReportResponseDTO reviewThreadPostReport(Long id, ThreadPostReportReviewRequestDTO requestDTO) {
        ThreadPostReport report = threadPostReportRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPostReport not found with id " + id));

        if (!requestDTO.getStatus().equals("REVIEWED") && !requestDTO.getStatus().equals("DISMISSED")) {
            throw new IllegalArgumentException("Invalid status. Must be REVIEWED or DISMISSED.");
        }

        String reviewerId = String.valueOf(AuditContext.getCurrentUser().getId());
        User reviewer = userRepository.findById(Long.parseLong(reviewerId))
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found with ID: " + reviewerId));

        report.setStatus(ThreadPostReportStatus.valueOf(requestDTO.getStatus()));
        report.setReviewedBy(reviewer);
        report.setLastModifiedDate(java.time.LocalDateTime.now());

        return ThreadPostReportMapper.toResponseDTO(threadPostReportRepository.save(report));
    }

    @Override
    public List<ThreadPostReportResponseDTO> getAllThreadPostReports() {
        List<ThreadPostReport> reports = threadPostReportRepository.findAll();
        return reports.stream()
                .map(ThreadPostReportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
