package com.hacof.identity.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

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
    @Column(name = "created_by", nullable = false)
    String createdBy;

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "DATETIME(6)", updatable = false, nullable = false)
    LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", columnDefinition = "DATETIME(6)", nullable = false)
    LocalDateTime lastModifiedDate;
}
