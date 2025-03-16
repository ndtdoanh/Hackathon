package com.hacof.hackathon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.dto.RoundLocationResponseDTO;
import com.hacof.hackathon.entity.RoundLocation;

@Mapper(componentModel = "spring")
public interface RoundLocationMapper {
    @Mapping(source = "id", target = "roundLocationId")
    @Mapping(source = "round.id", target = "roundId")
    @Mapping(source = "round.roundTitle", target = "roundTitle")
    @Mapping(source = "round.roundNumber", target = "roundNumber")
    @Mapping(source = "round.status", target = "roundStatus")
    @Mapping(source = "round.startTime", target = "roundStartTime")
    @Mapping(source = "round.endTime", target = "roundEndTime")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    @Mapping(source = "location.address", target = "locationAddress")
    @Mapping(source = "location.latitude", target = "locationLatitude")
    @Mapping(source = "location.longitude", target = "locationLongitude")
    @Mapping(source = "type", target = "roundLocationType")
    RoundLocationResponseDTO convertToResponseDTO(RoundLocation roundLocation);

    RoundLocation convertToEntity(RoundLocationDTO roundLocationDTO);
}
