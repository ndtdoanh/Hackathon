package com.hacof.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.constant.DeviceQualityStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_device_id", nullable = false)
    UserDevice userDevice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "device_quality_status", nullable = false)
    DeviceQualityStatus deviceQualityStatus;

    @Lob
    @Column(name = "note")
    String note;
}
