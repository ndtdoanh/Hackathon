package com.hacof.submission.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hacof.submission.util.AuditContext;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditCreatedBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = true, updatable = false)
    User createdBy;

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "DATETIME(6)", updatable = false, nullable = false)
    LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", columnDefinition = "DATETIME(6)", nullable = false)
    LocalDateTime lastModifiedDate;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = AuditContext.getCurrentUser();
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
