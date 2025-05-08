package com.hacof.communication.service;

import com.hacof.communication.dto.response.FileUrlResponse;

import java.util.List;

public interface FileUrlService {
    void deleteFileById(String id);

    List<FileUrlResponse> getFilesStartingWith(String prefix);
}
