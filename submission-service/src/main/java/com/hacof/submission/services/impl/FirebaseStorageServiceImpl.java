package com.hacof.submission.services.impl;

import com.google.firebase.cloud.StorageClient;
import com.hacof.submission.services.FirebaseStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // Lấy tên file và tạo đường dẫn lưu trữ
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        // Upload file lên Firebase Storage
        StorageClient.getInstance()
                .bucket()
                .create("uploads/" + fileName, inputStream, file.getContentType());

        // Trả về URL của file đã upload
        return "https://firebasestorage.googleapis.com/v0/b/"
                + StorageClient.getInstance().bucket().getName()
                + "/o/uploads%2F" + fileName + "?alt=media";
    }
}
