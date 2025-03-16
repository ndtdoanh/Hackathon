package com.hacof.submission.entity;

import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.submission.constant.NotificationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id")
    User sender; // Phuc note: should not be replaced by AuditCreatedBase

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    User recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    NotificationType type;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @Lob
    @Column(name = "metadata", columnDefinition = "JSON")
    String metadata; // JSON string for extra data

    @Column(name = "is_read")
    boolean isRead = false;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    List<NotificationDelivery> notificationDeliveries;
}
