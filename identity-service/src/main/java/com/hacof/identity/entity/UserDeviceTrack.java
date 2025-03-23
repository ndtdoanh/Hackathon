package com.hacof.identity.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.constant.DeviceQualityStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_device_tracks")
public class UserDeviceTrack extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_device_id")
    UserDevice userDevice;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_quality_status")
    DeviceQualityStatus deviceQualityStatus;

    @Lob
    @Column(name = "note")
    String note;

    @OneToMany(mappedBy = "userDeviceTrack", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FileUrl> fileUrls = new ArrayList<>();
}
