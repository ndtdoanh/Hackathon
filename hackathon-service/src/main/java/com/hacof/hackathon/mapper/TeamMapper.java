package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.TeamDTO;
import com.hacof.hackathon.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {HackathonMapper.class, TeamHackathonMapper.class}) // Thêm UserTeamMapper nếu cần thiết
public interface TeamMapper {

    @Mapping(target = "teamLeaderId", source = "teamLeader.id") // ánh xạ teamLeaderId từ teamLeader entity
    @Mapping(target = "teamMembers", source = "teamMembers") // ánh xạ teamMembers từ entity
    @Mapping(target = "teamHackathons", source = "teamHackathons") // ánh xạ teamHackathons từ entity
    TeamDTO toDto(Team team);

    @Mapping(target = "teamHackathons", source = "teamHackathons") // ánh xạ teamHackathons
    Team toEntity(TeamDTO teamDTO);
}
