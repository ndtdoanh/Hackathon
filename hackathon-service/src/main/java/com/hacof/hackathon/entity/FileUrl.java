package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "file_urls")
public class FileUrl extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_url")
    String fileUrl;

    @Column(name = "file_type")
    String fileType;

    @Column(name = "file_size")
    int fileSize;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    Submission submission;

    @ManyToOne
    @JoinColumn(name = "message_id")
    Message message;

    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;

    @ManyToOne
    @JoinColumn(name = "schedule_event_id")
    ScheduleEvent scheduleEvent;

    @ManyToOne
    @JoinColumn(name = "sponsorship_hackathon_detail_id")
    SponsorshipHackathonDetail sponsorshipHackathonDetail;

    @ManyToOne
    @JoinColumn(name = "device_id")
    Device device;

    @ManyToOne
    @JoinColumn(name = "user_device_id")
    UserDevice userDevice;

    @ManyToOne
    @JoinColumn(name = "user_device_track_id")
    UserDeviceTrack userDeviceTrack;
}
