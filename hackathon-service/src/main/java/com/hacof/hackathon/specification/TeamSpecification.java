package com.hacof.hackathon.specification;

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

    public static Specification<Team> hasHackathonId(Long hackathonId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hackathonId"), hackathonId);
    }

    public static Specification<Team> hasLeaderId(Long leaderId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("leaderId"), leaderId);
    }

    public static Specification<Team> hasMemberId(Long memberId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberId"), memberId);
    }
}
