package com.hacof.identity.entities;

import java.time.LocalDateTime;

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
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.constants.Audience;
import com.hacof.identity.constants.Priority;
import com.hacof.identity.constants.Type;

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
@Table(name = "Notifications")
public class Notification extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "title", nullable = false)
    String title;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @Enumerated(EnumType.STRING)
    @Column(name = "audience")
    Audience audience = Audience.ALL;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    Type type = Type.IN_APP;

    @ColumnDefault("0")
    @Column(name = "is_read")
    boolean isRead;

    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @Column(name = "sent_at")
    LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    Priority priority = Priority.MEDIUM;

    @Column(name = "expiry_date")
    LocalDateTime expiryDate;
}
