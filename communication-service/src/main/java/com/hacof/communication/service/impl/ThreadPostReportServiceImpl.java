package com.hacof.communication.service.impl;

import com.hacof.communication.constant.ThreadPostReportStatus;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadPostReportServiceImpl implements ThreadPostReportService {

    @Autowired
    private ThreadPostReportRepository threadPostReportRepository;

    @Autowired
    private ThreadPostRepository threadPostRepository;

    @Override
    public ThreadPostReportResponseDTO createThreadPostReport(ThreadPostReportRequestDTO requestDTO) {
        ThreadPost threadPost = threadPostRepository.findById(requestDTO.getThreadPostId())
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + requestDTO.getThreadPostId()));

        User createdBy = AuditContext.getCurrentUser();
        ThreadPostReport threadPostReport = ThreadPostReportMapper.toEntity(requestDTO, threadPost, createdBy);
        threadPostReport = threadPostReportRepository.save(threadPostReport);

        return ThreadPostReportMapper.toResponseDTO(threadPostReport);
    }

    @Override
    public List<ThreadPostReportResponseDTO> getReportsByThreadPostId(Long threadPostId) {
        List<ThreadPostReport> reports = threadPostReportRepository.findByThreadPostId(threadPostId);
        return reports.stream()
                .map(ThreadPostReportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
