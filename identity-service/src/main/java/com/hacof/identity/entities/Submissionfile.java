package com.hacof.identity.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.enums.FileType;
import com.hacof.identity.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "submissionfiles")
public class Submissionfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "round_id", nullable = false)
    private Competitionround round;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Lob
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    @Lob
    @Column(name = "feedback")
    private String feedback;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @Column(name = "uploaded_at")
    private Instant uploadedAt;
}
