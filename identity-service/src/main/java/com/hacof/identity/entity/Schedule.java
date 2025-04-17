package com.hacof.identity.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "schedules")
public class Schedule extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Hackathon hackathon;

    @Column(name = "name")
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleEvent> scheduleEvents = new ArrayList<>();
}
