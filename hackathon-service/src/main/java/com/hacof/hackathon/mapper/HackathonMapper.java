package com.hacof.hackathon.mapper;

import org.mapstruct.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.*;

@Mapper(componentModel = "spring")
public interface HackathonMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(hackathon.getId()))")
    @Mapping(
            target = "createdBy",
            expression = "java(hackathon.getCreatedBy() != null ? hackathon.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedBy",
            expression =
                    "java(hackathon.getLastModifiedBy() != null ? hackathon.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    HackathonDTO toDto(Hackathon hackathon);

    Hackathon toEntity(HackathonDTO hackathonDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", expression = "java(mapStringToUser(dto.getLastModifiedBy()))")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    void updateEntityFromDto(HackathonDTO dto, @MappingTarget Hackathon entity);

    default User mapStringToUser(String username) {
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
