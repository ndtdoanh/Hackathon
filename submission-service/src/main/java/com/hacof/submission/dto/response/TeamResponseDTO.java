package com.hacof.submission.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.submission.entity.Team;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponseDTO {
    String id;
    String name;
    UserResponse teamLeader;
    List<UserResponse> teamMembers;
    String bio;
    Boolean isDeleted;

    public TeamResponseDTO(Team team) {
        if (team != null) {
            this.id = String.valueOf(team.getId());
            this.name = team.getName();
            this.teamLeader = team.getTeamLeader() != null ? new UserResponse() : null;
            this.bio = team.getBio();
            this.isDeleted = team.getIsDeleted();

            if (team.getTeamMembers() != null) {
                this.teamMembers = team.getTeamMembers().stream()
                        .map(userTeam -> new UserResponse())
                        .collect(Collectors.toList());
            }
        }
    }
}
