package com.hacof.analytics.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hacof.analytics.constant.EventLabel;
import jakarta.persistence.*;

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
@Table(name = "schedule_events")
public class ScheduleEvent extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    Schedule schedule;

    @Column(name = "name")
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @Column(name = "location")
    String location;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;

    @Column(name = "is_recurring")
    boolean isRecurring;

    @Column(name = "recurrence_rule")
    String recurrenceRule;

    @OneToMany(mappedBy = "scheduleEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FileUrl> fileUrls = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "event_label")
    EventLabel eventLabel;
}
