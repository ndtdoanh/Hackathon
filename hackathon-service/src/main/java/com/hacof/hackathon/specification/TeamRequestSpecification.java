package com.hacof.hackathon.specification;

import java.time.LocalDateTime;

import jakarta.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;

public class TeamRequestSpecification {

    public static Specification<TeamRequest> hasStatus(TeamRequestStatus status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<TeamRequest> hasHackathonId(String hackathonId) {
        return (root, query, cb) -> {
            if (hackathonId == null) return null;
            return cb.equal(root.get("hackathon").get("id"), Long.parseLong(hackathonId));
        };
    }

    public static Specification<TeamRequest> hasTeamName(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<TeamRequest> hasMemberId(String userId) {
        return (root, query, cb) -> {
            if (userId == null) return null;
            Join<TeamRequest, TeamRequestMember> memberJoin = root.join("teamRequestMembers");
            return cb.equal(memberJoin.get("user").get("id"), Long.parseLong(userId));
        };
    }

    public static Specification<TeamRequest> createdDateBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from == null) return cb.lessThanOrEqualTo(root.get("createdDate"), to);
            if (to == null) return cb.greaterThanOrEqualTo(root.get("createdDate"), from);
            return cb.between(root.get("createdDate"), from, to);
        };
    }
}
