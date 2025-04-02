package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;

@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(round.getId()))")
    @Mapping(
            target = "hackathonId",
            expression = "java(round.getHackathon() != null ? String.valueOf(round.getHackathon().getId()) : null)")
    @Mapping(
            target = "createdByUserName",
            expression = "java(round.getCreatedBy() != null ? round.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression = "java(round.getLastModifiedBy() != null ? round.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    RoundDTO toDto(Round round);

    @Mapping(target = "hackathon", source = "hackathonId", qualifiedByName = "mapHackathonIdToEntity")
    Round toEntity(RoundDTO dto);

    @Named("mapHackathonIdToEntity")
    default Hackathon mapHackathonIdToEntity(String hackathonId) {
        if (hackathonId == null) return null;
        Hackathon hackathon = new Hackathon();
        hackathon.setId(Long.parseLong(hackathonId));
        return hackathon;
    }
}
