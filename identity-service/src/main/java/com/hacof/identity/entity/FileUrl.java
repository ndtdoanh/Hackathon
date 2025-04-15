package com.hacof.identity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    public FileUrl(String fileName, String fileUrl, String fileType, int fileSize, Device device) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.device = device;
    }

    public FileUrl(String fileName, String fileUrl, String fileType, int fileSize, UserDevice userDevice) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.userDevice = userDevice;
    }

    public FileUrl(String fileName, String fileUrl, String fileType, int fileSize, UserDeviceTrack userDeviceTrack) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.userDeviceTrack = userDeviceTrack;
    }

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    public FileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
