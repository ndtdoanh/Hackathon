package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;

public interface ThreadPostReportService {

    ThreadPostReportResponseDTO createThreadPostReport(ThreadPostReportRequestDTO requestDTO);

    List<ThreadPostReportResponseDTO> getReportsByThreadPostId(Long threadPostId);

    ThreadPostReportResponseDTO getThreadPostReport(Long id);

    ThreadPostReportResponseDTO updateThreadPostReport(Long id, ThreadPostReportRequestDTO requestDTO);

    void deleteThreadPostReport(Long id);
}
