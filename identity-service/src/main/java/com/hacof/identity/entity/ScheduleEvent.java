package com.hacof.identity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "schedule_events")
public class ScheduleEvent extends AuditUserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    Schedule schedule;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "location", nullable = false)
    String location;

    @NotNull
    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    @NotNull
    @Column(name = "is_recurring", nullable = false)
    boolean isRecurring;

    @Column(name = "recurrence_rule")
    String recurrenceRule;
}
