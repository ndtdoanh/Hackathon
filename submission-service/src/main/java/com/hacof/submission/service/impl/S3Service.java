package com.hacof.submission.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(InputStream inputStream, String originalFileName, long fileSize) throws IOException {
        // Tạo tên file ngẫu nhiên với UUID và tên file gốc
        String fileName = "hacofpt/" + UUID.randomUUID().toString() + "_" + originalFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileSize);

        // Tải file lên S3 với đường dẫn đầy đủ (bao gồm cả thư mục 'hacofpt/')
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));

        // Trả về URL của file đã tải lên
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}
