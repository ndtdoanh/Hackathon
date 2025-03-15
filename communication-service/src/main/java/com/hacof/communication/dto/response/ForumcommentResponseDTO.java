package com.hacof.communication.dto.response;

import java.time.Instant;

import com.hacof.communication.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumcommentResponseDTO {
    private Long id;
    private String comment;
    private Status status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
