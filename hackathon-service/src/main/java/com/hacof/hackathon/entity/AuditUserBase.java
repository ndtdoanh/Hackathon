package com.hacof.hackathon.entity;

import com.hacof.hackathon.util.AuditContext;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditUserBase {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    User createdBy;

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "DATETIME(6)")
    LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_user_id")
    User lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", columnDefinition = "DATETIME(6)")
    LocalDateTime lastModifiedDate;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdBy = AuditContext.getCurrentUser();
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.lastModifiedBy = AuditContext.getCurrentUser();
        this.lastModifiedDate = LocalDateTime.now();
    }
}
