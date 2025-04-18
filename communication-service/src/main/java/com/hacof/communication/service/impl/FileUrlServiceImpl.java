package com.hacof.communication.service.impl;

import java.util.Optional;

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
}
