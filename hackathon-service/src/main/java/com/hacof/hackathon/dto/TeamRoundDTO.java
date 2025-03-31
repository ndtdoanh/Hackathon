package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.hacof.hackathon.constant.TeamRoundStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRoundDTO {
    private String id;

    @NotBlank(message = "ID team không được để trống")
    private String teamId;

    @NotBlank(message = "ID vòng thi không được để trống")
    private String roundId;

    @NotNull(message = "Trạng thái không được để trống")
    private TeamRoundStatus status;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;

    // private List<TeamRoundJudgeDTO> judges;

    private LocalDateTime createdAt;
    private String createdByUserName;
    private LocalDateTime updatedAt;
    private String lastModifiedByUserName;
}
