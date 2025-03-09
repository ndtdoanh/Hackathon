package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Campus;

public class CampusSpecification {
    public static Specification<Campus> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Campus> hasName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Campus> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                location == null ? null : criteriaBuilder.equal(root.get("location"), location);
    }

    public static Specification<Campus> createdBy(String createdBy) {
        return (root, query, criteriaBuilder) ->
                createdBy == null ? null : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Campus> lastModifiedBy(String lastModifiedBy) {
        return (root, query, criteriaBuilder) ->
                lastModifiedBy == null ? null : criteriaBuilder.equal(root.get("lastModifiedBy"), lastModifiedBy);
    }
}
