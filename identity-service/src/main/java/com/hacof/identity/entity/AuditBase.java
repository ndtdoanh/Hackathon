package com.hacof.identity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditBase {

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "DATETIME(6)")
    LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", columnDefinition = "DATETIME(6)")
    LocalDateTime lastModifiedDate;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
