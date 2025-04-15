package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Round;

public class RoundSpecification {
    public static Specification<Round> hasId(String id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Round> hasRoundTitle(String roundTitle) {
        return (root, query, criteriaBuilder) ->
                roundTitle == null ? null : criteriaBuilder.equal(root.get("roundTitle"), roundTitle);
    }

    public static Specification<Round> hasRoundNumber(String roundNumber) {
        return (root, query, criteriaBuilder) ->
                roundNumber == null ? null : criteriaBuilder.equal(root.get("roundNumber"), roundNumber);
    }

    public static Specification<Round> hasCreatedBy(String createdBy) {
        return (root, query, criteriaBuilder) ->
                createdBy == null ? null : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Round> hasHackathonId(String hackathonId) {
        return (root, query, criteriaBuilder) -> hackathonId == null
                ? null
                : criteriaBuilder.equal(root.get("hackathon").get("id"), hackathonId);
    }
}
