package com.hacof.hackathon.specification;

import com.hacof.hackathon.entity.TeamHackathon;
import com.hacof.hackathon.entity.UserTeam;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Team;

public class TeamSpecification {
    public static Specification<Team> searchTeam(String search) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(search)) {
                return null;
            }
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")), "%" + search.toLowerCase() + "%"));
        };
    }

    public static Specification<Team> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Team> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Team> hasMemberId(Long memberId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberId"), memberId);
    }

    public static Specification<Team> hasLeaderId(Long leaderId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("teamLeader").get("id"), leaderId);
    }

//    // Filter teams by hackathonId
//    public static Specification<Team> hasHackathonId(Long hackathonId) {
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
//                root.join("teamHackathons").get("hackathon").get("id"), hackathonId);
//    }

    // Combine the two filters: by leaderId and hackathonId
//    public static Specification<Team> hasLeaderIdAndHackathonId(Long leaderId, Long hackathonId) {
//        return Specification.where(hasLeaderId(leaderId)).and(hasHackathonId(hackathonId));
//    }

    public static Specification<Team> hasUserIdInMembers(Long userId) {
        return (root, query, cb) -> {
            Join<Team, UserTeam> members = root.join("teamMembers", JoinType.INNER);
            return cb.equal(members.get("user").get("id"), userId);
        };
    }

    public static Specification<Team> hasHackathonId(Long hackathonId) {
        return (root, query, cb) -> {
            Join<Team, TeamHackathon> hackathons = root.join("teamHackathons", JoinType.INNER);
            return cb.equal(hackathons.get("hackathon").get("id"), hackathonId);
        };
    }

    public static Specification<Team> hasUserIdAndHackathonId(Long userId, Long hackathonId) {
        return Specification.where(hasUserIdInMembers(userId)).and(hasHackathonId(hackathonId));
    }
}
