package com.hacof.hackathon.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.TeamRequest;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TeamRequestMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(teamRequest.getId()))")
    @Mapping(target = "hackathonId", expression = "java(String.valueOf(teamRequest.getHackathon().getId()))")
    @Mapping(target = "reviewedBy", source = "reviewedBy")
    @Mapping(
            target = "createdByUserName",
            expression = "java(teamRequest.getCreatedBy() != null ? teamRequest.getCreatedBy().getUsername() : null)")
    @Mapping(
            target = "lastModifiedByUserName",
            expression =
                    "java(teamRequest.getLastModifiedBy() != null ? teamRequest.getLastModifiedBy().getUsername() : null)")
    @Mapping(target = "createdAt", source = "createdDate")
    @Mapping(target = "updatedAt", source = "lastModifiedDate")
    TeamRequestDTO toDto(TeamRequest teamRequest);

    @Mapping(target = "hackathon.id", source = "hackathonId")
    @Mapping(target = "reviewedBy", source = "reviewedBy")
    TeamRequest toEntity(TeamRequestDTO teamRequestDTO);

    default List<String> map(List<FileUrl> value) {
        return value.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }

    default List<FileUrl> mapToFileUrlList(List<String> value) {
        return value.stream().map(FileUrl::new).collect(Collectors.toList());
    }
}
