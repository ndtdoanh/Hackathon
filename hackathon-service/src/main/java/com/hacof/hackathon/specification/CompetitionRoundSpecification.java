package com.hacof.hackathon.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.constant.RoundType;
import com.hacof.hackathon.entity.CompetitionRound;

public class CompetitionRoundSpecification {
    public static Specification<CompetitionRound> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<CompetitionRound> hasName(RoundType name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<CompetitionRound> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<CompetitionRound> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                startDate == null ? null : criteriaBuilder.equal(root.get("startDate"), startDate);
    }

    public static Specification<CompetitionRound> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                endDate == null ? null : criteriaBuilder.equal(root.get("endDate"), endDate);
    }

    public static Specification<CompetitionRound> hasMaxTeam(Integer maxTeam) {
        return (root, query, criteriaBuilder) ->
                maxTeam == null ? null : criteriaBuilder.equal(root.get("maxTeam"), maxTeam);
    }

    public static Specification<CompetitionRound> isVideoRound(Boolean isVideoRound) {
        return (root, query, criteriaBuilder) ->
                isVideoRound == null ? null : criteriaBuilder.equal(root.get("isVideoRound"), isVideoRound);
    }

    public static Specification<CompetitionRound> hasHackathonId(Long hackathonId) {
        return (root, query, criteriaBuilder) -> hackathonId == null
                ? null
                : criteriaBuilder.equal(root.get("hackathon").get("id"), hackathonId);
    }
}
