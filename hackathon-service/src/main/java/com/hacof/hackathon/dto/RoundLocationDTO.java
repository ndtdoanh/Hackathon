package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.RoundLocationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundLocationDTO {
    private String id;
    private RoundDTO round;
    private LocationDTO location;
    private RoundLocationType type;
}
