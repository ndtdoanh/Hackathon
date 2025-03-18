package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.RoundLocation;

public class RoundLocationSpecification {

    public static Specification<RoundLocation> hasId(Long id) {
        return (root, query, criteriaBuilder) ->
                id == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<RoundLocation> hasRoundId(Long roundId) {
        return (root, query, criteriaBuilder) ->
                roundId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("roundId"), roundId);
    }

    public static Specification<RoundLocation> hasLocationId(Long locationId) {
        return (root, query, criteriaBuilder) -> locationId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("locationId"), locationId);
    }

    public static Specification<RoundLocation> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                type == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("type"), type);
    }
}
