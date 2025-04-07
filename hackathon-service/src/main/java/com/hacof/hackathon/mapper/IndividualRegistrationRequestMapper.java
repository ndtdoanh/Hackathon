package com.hacof.hackathon.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.dto.IndividualRegistrationRequestDTO;
import com.hacof.hackathon.dto.UserDTO;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.IndividualRegistrationRequest;
import com.hacof.hackathon.entity.User;

@Mapper(componentModel = "spring")
public interface IndividualRegistrationRequestMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(individualRegistrationRequest.getId()))")
    @Mapping(target = "hackathon", source = "hackathon", qualifiedByName = "hackathonToHackathonDTO")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "userToUserDTO")
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

    IndividualRegistrationRequest toEntity(IndividualRegistrationRequestDTO individualRegistrationRequestDTO);

    @Named("userToUserDTO")
    default UserDTO userToUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(String.valueOf(user.getId()));
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setBio(user.getBio());
        userDTO.setCreatedAt(user.getCreatedDate());
        userDTO.setUpdatedAt(user.getLastModifiedDate());
        return userDTO;
    }

    @Named("hackathonToHackathonDTO")
    default HackathonDTO hackathonToHackathonDTO(Hackathon hackathon) {
        if (hackathon == null) return null;
        HackathonDTO hackathonDTO = new HackathonDTO();
        hackathonDTO.setId(String.valueOf(hackathon.getId()));
        hackathonDTO.setTitle(hackathon.getTitle());
        hackathonDTO.setSubTitle(hackathon.getSubTitle());
        hackathonDTO.setBannerImageUrl(hackathon.getBannerImageUrl());
        hackathonDTO.setEnrollStartDate(hackathon.getEnrollStartDate());
        hackathonDTO.setEnrollEndDate(hackathon.getEnrollEndDate());
        hackathonDTO.setStartDate(hackathon.getStartDate());
        hackathonDTO.setEndDate(hackathon.getEndDate());
        hackathonDTO.setInformation(hackathon.getInformation());
        hackathonDTO.setDescription(hackathon.getDescription());
        hackathonDTO.setContact(hackathon.getContact());
        hackathonDTO.setCategory(hackathon.getCategory().name());
        hackathonDTO.setOrganization(hackathon.getOrganization().name());
        hackathonDTO.setStatus(
                hackathon.getStatus() != null ? hackathon.getStatus().name() : null);
        hackathonDTO.setCreatedAt(hackathon.getCreatedDate());
        hackathonDTO.setUpdatedAt(hackathon.getLastModifiedDate());
        return hackathonDTO;
    }

    default List<String> map(List<FileUrl> value) {
        return value.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }

    default List<FileUrl> mapToFileUrlList(List<String> value) {
        return value.stream().map(FileUrl::new).collect(Collectors.toList());
    }
}
