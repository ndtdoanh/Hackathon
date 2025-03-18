package com.hacof.hackathon.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.*;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HackathonMapper {

    @Mapping(target = "roundNames", source = "rounds", qualifiedByName = "roundsToNames")
    @Mapping(target = "currentTeamCount", source = "teamHackathons", qualifiedByName = "countTeams")
    @Mapping(target = "registrationCount", source = "userHackathons", qualifiedByName = "countRegistrations")
    @Mapping(target = "mentorCount", source = "mentorshipRequests", qualifiedByName = "countMentors")
    @Mapping(target = "sponsorCount", source = "sponsorshipHackathons", qualifiedByName = "countSponsors")
    HackathonDTO toDTO(Hackathon hackathon);

    @Mapping(target = "rounds", ignore = true)
    @Mapping(target = "teamHackathons", ignore = true)
    @Mapping(target = "hackathonResults", ignore = true)
    @Mapping(target = "userHackathons", ignore = true)
    @Mapping(target = "teamRequests", ignore = true)
    @Mapping(target = "individualRegistrationRequests", ignore = true)
    @Mapping(target = "mentorshipRequests", ignore = true)
    @Mapping(target = "mentorshipSessionRequests", ignore = true)
    @Mapping(target = "sponsorshipHackathons", ignore = true)
    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    Hackathon toEntity(HackathonDTO hackathonDTO);

    @Named("roundsToNames")
    default List<String> roundsToNames(List<Round> rounds) {
        if (rounds == null) return List.of();
        return rounds.stream().map(Round::getRoundTitle).collect(Collectors.toList());
    }

    @Named("countTeams")
    default int countTeams(List<TeamHackathon> teams) {
        return teams != null ? teams.size() : 0;
    }

    @Named("countRegistrations")
    default int countRegistrations(List<UserHackathon> registrations) {
        return registrations != null ? registrations.size() : 0;
    }

    @Named("countMentors")
    default int countMentors(List<MentorshipRequest> mentors) {
        return mentors != null ? mentors.size() : 0;
    }

    @Named("countSponsors")
    default int countSponsors(List<SponsorshipHackathon> sponsors) {
        return sponsors != null ? sponsors.size() : 0;
    }

    default List<HackathonDTO> toDTOs(List<Hackathon> hackathons) {
        return hackathons.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
