package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.TeamRequestMemberDTO;
import com.hacof.hackathon.entity.TeamRequestMember;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TeamRequestMemberMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(teamRequestMember.getId()))")
    @Mapping(target = "teamRequestId", source = "teamRequest.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "respondedAt", source = "respondedAt")
    @Mapping(
            target = "createdByUserName",
            expression =
                    "java(teamRequestMember.getCreatedBy() != null ? teamRequestMember.getCreatedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(teamRequestMember.getLastModifiedBy() != null ? teamRequestMember.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    TeamRequestMemberDTO toDto(TeamRequestMember teamRequestMember);

    @Mapping(source = "teamRequestId", target = "teamRequest.id")
    @Mapping(source = "user.id", target = "user.id")
    TeamRequestMember toEntity(TeamRequestMemberDTO teamRequestMemberDTO);
}
