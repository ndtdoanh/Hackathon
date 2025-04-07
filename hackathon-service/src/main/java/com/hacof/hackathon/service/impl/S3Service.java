package com.hacof.hackathon.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.FieldDefaults;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class S3Service {
    S3Client s3Client;
    String bucketName;

    public S3Service(
            @Value("${aws.s3.access-key}") String accessKey,
            @Value("${aws.s3.secret-key}") String secretKey,
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) {
        String key = Paths.get(file.getOriginalFilename()).getFileName().toString();
        try {
            s3Client.putObject(
                    PutObjectRequest.builder().bucket(bucketName).key(key).build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
            return s3Client.utilities()
                    .getUrl(builder -> builder.bucket(bucketName).key(key))
                    .toExternalForm();
        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public List<String> uploadFiles(MultipartFile[] files) {
        return Stream.of(files).map(this::uploadFile).collect(Collectors.toList());
    }

    public List<String> getAllFiles() {
        ListObjectsV2Request request =
                ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response result = s3Client.listObjectsV2(request);
        return result.contents().stream()
                .map(S3Object::key)
                .map(key -> s3Client.utilities()
                        .getUrl(builder -> builder.bucket(bucketName).key(key))
                        .toExternalForm())
                .collect(Collectors.toList());
    }
}
