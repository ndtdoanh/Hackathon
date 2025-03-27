package com.hacof.identity.entity;

import com.hacof.identity.constant.RoleType;
import jakarta.persistence.*;

import com.hacof.identity.constant.NotificationMethod;
import com.hacof.identity.constant.NotificationStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    User recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    RoleType role;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    NotificationMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    NotificationStatus status;
}
