package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.Hackathon;

public class HackathonSpecification {
    public static Specification<Hackathon> idEquals(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Hackathon> hasName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }

    //    public static Specification<Hackathon> nameContains(String name) {
    //        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    //    }
    //
    //    public static Specification<Hackathon> startDateGreaterThan(LocalDateTime startDate) {
    //        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("startDate"), startDate);
    //    }
    //
    //    public static Specification<Hackathon> endDateLessThan(LocalDateTime endDate) {
    //        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("endDate"), endDate);
    //    }
    //
    //    public static Specification<Hackathon> statusEquals(Status status) {
    //        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    //    }
    //
    //    public static Specification<Hackathon> statusIn(Status... status) {
    //        return (root, query, criteriaBuilder) -> root.get("status").in((Object[]) status);
    //    }
    //
    //    public static Specification<Hackathon> nameAndStatus(String name, Status status) {
    //        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
    //                criteriaBuilder.like(root.get("name"), "%" + name + "%"),
    //                criteriaBuilder.equal(root.get("status"), status));
    //    }

    public static Specification<Hackathon> bannerImageUrlContains(String bannerImageUrl) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("bannerImageUrl"), "%" + bannerImageUrl + "%");
    }

    public static Specification<Hackathon> descriptionContains(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Hackathon> numberRoundEquals(Integer numberRound) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("numberRound"), numberRound);
    }

    public static Specification<Hackathon> maxTeamsEquals(Integer maxTeams) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("maxTeams"), maxTeams);
    }

    public static Specification<Hackathon> minTeamSizeEquals(Integer minTeamSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("minTeamSize"), minTeamSize);
    }

    public static Specification<Hackathon> maxTeamSizeEquals(Integer maxTeamSize) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("maxTeamSize"), maxTeamSize);
    }

    public static Specification<Hackathon> statusEquals(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), Status.valueOf(status));
    }

    public static Specification<Hackathon> createdByEquals(String createdBy) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Hackathon> lastModifiedByEquals(String lastModifiedBy) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastModifiedBy"), lastModifiedBy);
    }
}
