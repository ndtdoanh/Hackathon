package com.hacof.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.hacof.identity.constant.ThreadPostReportStatus;

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
public class ThreadPostReport extends AuditUserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "thread_post_id", nullable = false)
    ThreadPost threadPost;

    @Lob
    @Column(name = "reason", nullable = false)
    String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    ThreadPostReportStatus status = ThreadPostReportStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;
}
