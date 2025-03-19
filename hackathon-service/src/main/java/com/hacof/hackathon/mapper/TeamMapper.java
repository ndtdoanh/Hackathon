package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.dto.UserTeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.UserTeam;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class})
public interface TeamMapper {
    @Mapping(source = "hackathon.id", target = "hackathonId")
    @Mapping(source = "teamLeader.id", target = "teamLeaderId")
    TeamDTO toDTO(Team team);

    @Mapping(target = "hackathon", ignore = true)
    @Mapping(target = "teamLeader", ignore = true)
    @Mapping(target = "teamMembers", ignore = true)
    @Mapping(target = "teamHackathons", ignore = true)
    @Mapping(target = "teamRounds", ignore = true)
    @Mapping(target = "hackathonResults", ignore = true)
    @Mapping(target = "mentorshipRequests", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Team toEntity(TeamDTO dto);

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "user.id", target = "userId")
    UserTeamDTO toUserTeamDTO(UserTeam userTeam);
}
