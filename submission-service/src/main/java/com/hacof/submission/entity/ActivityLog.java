package com.hacof.submission.entity;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.submission.constant.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "activity_logs")
public class ActivityLog extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "action")
    String action;

    @Column(name = "target")
    String target;

    //    @Column(name = "changed_fields")
    //    @JdbcTypeCode(SqlTypes.JSON)
    //    Map<String, Object> changedFields;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

    @Column(name = "ip_address")
    String ipAddress;

    @Lob
    @Column(name = "device_details")
    String deviceDetails;
}
