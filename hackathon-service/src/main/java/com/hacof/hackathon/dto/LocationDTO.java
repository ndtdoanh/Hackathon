package com.hacof.hackathon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDTO {
    Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    String name;
    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address must be less than 255 characters")
    String address;
    @NotNull(message = "Latitude is required")
    Double latitude;
    @NotNull(message = "Longitude is required")
    Double longitude;
}