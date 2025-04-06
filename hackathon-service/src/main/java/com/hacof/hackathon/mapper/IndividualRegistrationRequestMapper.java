package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface IndividualRegistrationRequestMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(individualRegistrationRequest.getId()))")
    @Mapping(
            target = "hackathonId",
            expression = "java(String.valueOf(individualRegistrationRequest.getHackathon().getId()))")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "userToString")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(individualRegistrationRequest.getCreatedBy() != null ? individualRegistrationRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(individualRegistrationRequest.getLastModifiedBy() != null ? individualRegistrationRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    IndividualRegistrationRequestDTO toDto(IndividualRegistrationRequest individualRegistrationRequest);

    @Mapping(
            target = "hackathon.id",
            expression = "java(Long.parseLong(individualRegistrationRequestDTO.getHackathonId()))")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "stringToUser")
    IndividualRegistrationRequest toEntity(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    // void updateEntityFromDto(IndividualRegistrationRequestDTO dto, @MappingTarget IndividualRegistrationRequest
    // entity);

    @Named("userToString")
    default String userToString(User user) {
        return user != null ? String.valueOf(user.getId()) : null;
    }

    @Named("stringToUser")
    default User stringToUser(String id) {
        if (id == null || id.isBlank()) return null;
        User user = new User();
        user.setId(Long.parseLong(id));
        return user;
    }
}
