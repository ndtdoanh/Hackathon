package com.hacof.submission.entity;

import jakarta.persistence.*;

import com.hacof.submission.constant.NotificationMethod;
import com.hacof.submission.constant.NotificationStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notification_deliveries")
public class NotificationDelivery extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    NotificationMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    NotificationStatus status;
}
