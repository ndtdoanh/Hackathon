package com.hacof.communication.service.impl;

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
        Long threadPostId = Long.parseLong(requestDTO.getThreadPostId());
        ThreadPost threadPost = threadPostRepository
                .findById(threadPostId)
                .orElseThrow(() -> new IllegalArgumentException("ThreadPost not found with id " + threadPostId));

        User createdBy = AuditContext.getCurrentUser();
        ThreadPostReport threadPostReport = ThreadPostReportMapper.toEntity(requestDTO, threadPost, createdBy);
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
}
