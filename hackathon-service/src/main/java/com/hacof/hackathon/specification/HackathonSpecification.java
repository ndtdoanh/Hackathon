package com.hacof.hackathon.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Hackathon;

public class HackathonSpecification {
    public static Specification<Hackathon> hasId(Long id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.get("id"), id);
        };
    }

    public static Specification<Hackathon> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null) return null;
            return cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Hackathon> startDateBetween(LocalDateTime startFrom, LocalDateTime startTo) {
        return (root, query, cb) -> {
            if (startFrom == null || startTo == null) return null;
            return cb.between(root.get("startDate"), startFrom, startTo);
        };
    }

    public static Specification<Hackathon> endDateBetween(LocalDateTime endFrom, LocalDateTime endTo) {
        return (root, query, cb) -> {
            if (endFrom == null || endTo == null) return null;
            return cb.between(root.get("endDate"), endFrom, endTo);
        };
    }

    public static Specification<Hackathon> hasTeamSizeRange(Integer minSize, Integer maxSize) {
        return (root, query, cb) -> {
            if (minSize == null || maxSize == null) return null;
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get("minTeamSize"), minSize),
                    cb.lessThanOrEqualTo(root.get("maxTeamSize"), maxSize));
        };
    }

    public static Specification<Hackathon> hasMaxTeamsGreaterThan(Integer maxTeams) {
        return (root, query, cb) -> {
            if (maxTeams == null) return null;
            return cb.greaterThanOrEqualTo(root.get("maxTeams"), maxTeams);
        };
    }

    public static Specification<Hackathon> hasNumberRound(Integer numberRound) {
        return (root, query, cb) -> {
            if (numberRound == null) return null;
            return cb.equal(root.get("numberRound"), numberRound);
        };
    }

    public static Specification<Hackathon> isActive() {
        return (root, query, cb) ->
                cb.or(cb.equal(root.get("status"), "OPEN"), cb.equal(root.get("status"), "IN_PROGRESS"));
    }

    public static Specification<Hackathon> isUpcoming() {
        return (root, query, cb) -> cb.greaterThan(root.get("startDate"), LocalDateTime.now());
    }
}
