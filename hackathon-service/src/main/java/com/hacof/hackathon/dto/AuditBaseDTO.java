package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditBaseDTO {
    @NotBlank(message = "Created by is mandatory")
    String createdBy;

    @CreatedDate
    LocalDateTime createdDate;

    @NotBlank(message = "Last modified by is mandatory")
    String lastModifiedBy;

    @LastModifiedDate
    LocalDateTime lastModifiedDate;
}
