package com.hacof.submission.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStorageService {
    String uploadFile(MultipartFile file) throws IOException;
}
