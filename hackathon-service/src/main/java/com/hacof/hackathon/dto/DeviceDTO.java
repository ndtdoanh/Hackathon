package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.DeviceStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDTO {
    private Long id;

    @NotNull(message = "Hackathon ID is mandatory")
    private Long hackathonId;

    @NotNull(message = "Round ID is mandatory")
    private Long roundId;

    @NotNull(message = "Device ID is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Device status is mandatory")
    private DeviceStatus status;
}
