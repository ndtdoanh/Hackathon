package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.response.FileUrlResponse;

public interface FileUrlService {
    void deleteFileById(String id);

    List<FileUrlResponse> getFilesStartingWith(String prefix);
}
