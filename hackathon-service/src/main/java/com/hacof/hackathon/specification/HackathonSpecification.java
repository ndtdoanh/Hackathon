package com.hacof.hackathon.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Hackathon;

import io.micrometer.common.util.StringUtils;

public class HackathonSpecification {
    public static Specification<Hackathon> searchByKeyword(String keyword) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(keyword)) {
                return null;
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), likePattern),
                    cb.like(cb.lower(root.get("subTitle")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("information")), likePattern),
                    cb.like(cb.lower(root.get("category")), likePattern),
                    cb.like(cb.lower(root.get("organization")), likePattern));
        };
    }

    public static Specification<Hackathon> hasStatus(String status) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(status)) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Hackathon> datesBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Hackathon> teamSizeRange(Integer minTeamSize, Integer maxTeamSize) {
        return (root, query, cb) -> {
            if (minTeamSize == null && maxTeamSize == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (minTeamSize != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("minTeamSize"), minTeamSize));
            }
            if (maxTeamSize != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("maxTeamSize"), maxTeamSize));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Hackathon> byCategory(String category) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(category)) {
                return null;
            }
            return cb.equal(root.get("category"), category);
        };
    }
}
