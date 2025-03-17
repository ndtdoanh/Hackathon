package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Location;

public class LocationSpecification {
    public static Specification<Location> hasId(Long id) {
        return (root, query, criteriaBuilder) ->
                id == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Location> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Location> hasAddress(String address) {
        return (root, query, criteriaBuilder) -> address == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    public static Specification<Location> hasLatitude(Double latitude) {
        return (root, query, criteriaBuilder) -> latitude == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("latitude"), latitude);
    }

    public static Specification<Location> hasLongitude(Double longitude) {
        return (root, query, criteriaBuilder) -> longitude == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("longitude"), longitude);
    }

    public static Specification<Location> createdBy(String createdBy) {
        return (root, query, criteriaBuilder) -> createdBy == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Location> lastModifiedBy(String lastModifiedBy) {
        return (root, query, criteriaBuilder) -> lastModifiedBy == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("lastModifiedBy"), lastModifiedBy);
    }
}
