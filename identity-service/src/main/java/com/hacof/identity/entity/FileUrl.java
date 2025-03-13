package com.hacof.identity.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "file_urls")
public class FileUrl extends AuditUserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "file_url", nullable = false)
    String fileUrl;

    @Column(name = "file_type", nullable = false)
    String fileType;

    @Column(name = "file_size", nullable = false)
    int fileSize;

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    Submission submission;
}
