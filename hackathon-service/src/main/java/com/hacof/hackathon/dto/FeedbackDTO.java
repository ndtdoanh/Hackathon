package com.hacof.hackathon.dto;

import com.hacof.hackathon.entity.AuditCreatedBase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDTO extends AuditCreatedBase {
    String id;
    HackathonDTO hackathon;
    String hackathonId;
    UserDTO mentor;
    String mentorId;
    TeamDTO team;
    String teamId;
    List<FeedbackDetailDTO> feedbackDetails;
}
