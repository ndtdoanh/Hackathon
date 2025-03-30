package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface RoundMapper {

    @Mapping(target = "roundLocations", ignore = true)
    @Mapping(source = "hackathon.id", target = "hackathonId")
    //    @Mapping(target = "createdByUserName", source = "createdBy", qualifiedByName = "mapUserToString")
    //    @Mapping(target = "createdAt", source = "createdDate")
    //    @Mapping(target = "lastModifiedByUserName", source = "lastModifiedBy", qualifiedByName = "mapUserToString")
    //    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    RoundDTO toDto(Round round);

    Round toEntity(RoundDTO dto);

    @Named("mapUserToString")
    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
}
