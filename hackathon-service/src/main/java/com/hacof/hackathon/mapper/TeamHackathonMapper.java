package com.hacof.hackathon.mapper;

import com.hacof.hackathon.dto.TeamHackathonDTO;
import com.hacof.hackathon.entity.FileUrl;
import com.hacof.hackathon.entity.TeamHackathon;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeamHackathonMapper {
    // @Mapping(target = "team", ignore = true)
    TeamHackathonDTO toDto(TeamHackathon teamHackathon);

    // @Mapping(target = "team", ignore = true)
    TeamHackathon toEntity(TeamHackathonDTO teamHackathonDTO);

    default List<String> map(List<FileUrl> value) {
        return value.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
    }

    default List<FileUrl> mapToFileUrlList(List<String> value) {
        return value.stream().map(FileUrl::new).collect(Collectors.toList());
    }
}
