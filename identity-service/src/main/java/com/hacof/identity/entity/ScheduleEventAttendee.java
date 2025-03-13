package com.hacof.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.hacof.identity.constant.ScheduleEventStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "schedule_event_attendees")
public class ScheduleEventAttendee extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_event_id", nullable = false)
    ScheduleEvent scheduleEvent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    ScheduleEventStatus status = ScheduleEventStatus.INVITED;
}
