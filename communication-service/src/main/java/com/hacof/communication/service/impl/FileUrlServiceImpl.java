package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.communication.dto.response.FileUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.repository.FileUrlRepository;
import com.hacof.communication.service.FileUrlService;

@Service
public class FileUrlServiceImpl implements FileUrlService {

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Override
    public void deleteFileById(String id) {
        Optional<FileUrl> fileUrlOptional = fileUrlRepository.findById(Long.valueOf(id));

        if (!fileUrlOptional.isPresent()) {
            throw new IllegalArgumentException("File with ID " + id + " not found!");
        }

        fileUrlRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<FileUrlResponse> getFilesStartingWith(String prefix) {
        return fileUrlRepository.findAll().stream()
                .filter(file -> file.getFileName().startsWith(prefix))
                .map(file -> FileUrlResponse.builder()
                        .id(String.valueOf(file.getId()))
                        .fileName(file.getFileName())
                        .fileUrl(file.getFileUrl())
                        .fileType(file.getFileType())
                        .fileSize(file.getFileSize())
                        .createdAt(file.getCreatedDate())
                        .updatedAt(file.getLastModifiedDate())
                        .build())
                .collect(Collectors.toList());
    }
}
