package com.hacof.hackathon.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Hackathon;

public class HackathonSpecification {
    public static Specification<Hackathon> filter(
            String id,
            String title,
            String subTitle,
            String bannerImageUrl,
            LocalDateTime enrollStartDate,
            LocalDateTime enrollEndDate,
            Integer enrollmentCount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String information,
            String description,
            String contact,
            String category,
            String organization,
            String enrollmentStatus,
            String status,
            Integer minimumTeamMembers,
            Integer maximumTeamMembers) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(id).ifPresent(value -> predicates.add(cb.equal(root.get("id"), value)));
            Optional.ofNullable(title)
                    .ifPresent(value ->
                            predicates.add(cb.like(cb.lower(root.get("title")), "%" + value.toLowerCase() + "%")));
            Optional.ofNullable(subTitle)
                    .ifPresent(value ->
                            predicates.add(cb.like(cb.lower(root.get("subtitle")), "%" + value.toLowerCase() + "%")));
            Optional.ofNullable(bannerImageUrl)
                    .ifPresent(value -> predicates.add(cb.equal(root.get("bannerImageUrl"), value)));
            Optional.ofNullable(enrollStartDate)
                    .ifPresent(value -> predicates.add(cb.greaterThanOrEqualTo(root.get("enrollStartDate"), value)));
            Optional.ofNullable(enrollEndDate)
                    .ifPresent(value -> predicates.add(cb.lessThanOrEqualTo(root.get("enrollEndDate"), value)));
            Optional.ofNullable(enrollmentCount)
                    .ifPresent(value -> predicates.add(cb.equal(root.get("enrollmentCount"), value)));
            Optional.ofNullable(startDate)
                    .ifPresent(value -> predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), value)));
            Optional.ofNullable(endDate)
                    .ifPresent(value -> predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), value)));
            Optional.ofNullable(information)
                    .ifPresent(value -> predicates.add(
                            cb.like(cb.lower(root.get("information")), "%" + value.toLowerCase() + "%")));
            Optional.ofNullable(description)
                    .ifPresent(value -> predicates.add(
                            cb.like(cb.lower(root.get("description")), "%" + value.toLowerCase() + "%")));
            Optional.ofNullable(contact).ifPresent(value -> predicates.add(cb.equal(root.get("contact"), value)));
            Optional.ofNullable(category).ifPresent(value -> predicates.add(cb.equal(root.get("category"), value)));
            Optional.ofNullable(organization)
                    .ifPresent(value -> predicates.add(cb.equal(root.get("organization"), value)));
            Optional.ofNullable(enrollmentStatus)
                    .ifPresent(value -> predicates.add(cb.equal(root.get("enrollmentStatus"), value)));
            Optional.ofNullable(status).ifPresent(value -> predicates.add(cb.equal(root.get("status"), value)));

            if (minimumTeamMembers != null && maximumTeamMembers != null) {
                predicates.add(cb.and(
                        cb.greaterThanOrEqualTo(root.get("minimumTeamMembers"), minimumTeamMembers),
                        cb.lessThanOrEqualTo(root.get("maximumTeamMembers"), maximumTeamMembers)));
            }

            // if we have no filter, return empty predicate to avoid error -> return all Hackathon
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
