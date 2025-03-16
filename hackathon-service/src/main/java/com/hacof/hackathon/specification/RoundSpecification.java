package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Round;

public class RoundSpecification {
    public static Specification<Round> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Round> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Round> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("description"), description);
    }

    public static Specification<Round> hasStartDate(String startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startDate"), startDate);
    }

    public static Specification<Round> hasEndDate(String endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endDate"), endDate);
    }

    public static Specification<Round> hasMaxTeam(int maxTeam) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("maxTeam"), maxTeam);
    }

    public static Specification<Round> hasIsVideoRound(boolean isVideoRound) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isVideoRound"), isVideoRound);
    }
}
