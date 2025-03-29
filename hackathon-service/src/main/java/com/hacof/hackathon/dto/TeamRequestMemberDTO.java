package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.AuditBase;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestMemberDTO extends AuditBase {
    String id;
    String teamRequestId;
    String userId;
    Status status;
    LocalDateTime respondedAt;
}
