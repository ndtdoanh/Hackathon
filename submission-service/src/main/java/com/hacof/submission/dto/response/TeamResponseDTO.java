package com.hacof.submission.dto.response;

import com.hacof.submission.entity.Team;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponseDTO {
    Long id;
    String name;
    UserResponse teamLeader;
    List<UserResponse> teamMembers;
    String bio;
    Boolean isDeleted;

    public TeamResponseDTO(Team team) {
        if (team != null) {
            this.id = team.getId();
            this.name = team.getName();
            this.teamLeader = team.getTeamLeader() != null ? new UserResponse() : null;
            this.bio = team.getBio();
            this.isDeleted = team.isDeleted();

            if (team.getTeamMembers() != null) {
                this.teamMembers = team.getTeamMembers().stream()
                        .map(userTeam -> new UserResponse())
                        .collect(Collectors.toList());
            }
        }
    }
}
