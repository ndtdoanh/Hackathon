package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.ThreadPostReportStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "thread_post_reports")
public class ThreadPostReport extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_post_id")
    ThreadPost threadPost;

    @Lob
    @Column(name = "reason")
    String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ThreadPostReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;
}
