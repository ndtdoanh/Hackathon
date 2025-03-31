package com.hacof.hackathon.mapper;

// @Mapper(
//        componentModel = "spring",
//        uses = {
//            UserMapper.class,
//            HackathonMapper.class,
//            UserTeamMapper.class,
//            MentorshipSessionRequestMapper.class,
//            TeamHackathonMapper.class,
//            TeamRoundMapper.class,
//            HackathonResultMapper.class,
//            MentorshipRequestMapper.class,
//            FeedbackMapper.class
//        })
// public interface TeamMapper {
//    TeamDTO toDto(Team team);
//
//    Team toEntity(TeamDTO teamDTO);
//
//    void updateEntityFromDto(TeamDTO teamDTO, @MappingTarget Team team);
// }

import org.mapstruct.Mapper;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDTO toDto(Team team);

    Team toEntity(TeamDTO teamDTO);

    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
