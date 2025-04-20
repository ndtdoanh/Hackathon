package com.hacof.hackathon.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.experimental.FieldDefaults;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class S3Service {
    S3Client s3Client;
    String bucketName;
    AmazonS3 amazonS3;

    public S3Service(
            @Value("${aws.s3.access-key}") String accessKey,
            @Value("${aws.s3.secret-key}") String secretKey,
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName,
            AmazonS3 amazonS3) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.bucketName = bucketName;
        this.amazonS3 = amazonS3;
    }

    public String uplFile(InputStream inputStream, String originalFileName, long fileSize, String contentType) {
        String fileName = "hacofpt/" + UUID.randomUUID() + "_" + originalFileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileSize);
        metadata.setContentType(contentType);

        com.amazonaws.services.s3.model.PutObjectRequest putObjectRequest =
                new com.amazonaws.services.s3.model.PutObjectRequest(bucketName, fileName, inputStream, metadata);

        amazonS3.putObject(putObjectRequest);

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "default-file-name";
        String key = Paths.get(originalFilename).getFileName().toString();
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
